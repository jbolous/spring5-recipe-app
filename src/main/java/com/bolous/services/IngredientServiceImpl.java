package com.bolous.services;

import com.bolous.commands.IngredientCommand;
import com.bolous.converters.IngredientCommandToIngredient;
import com.bolous.converters.IngredientToIngredientCommand;
import com.bolous.domain.Ingredient;
import com.bolous.domain.Recipe;
import com.bolous.exceptions.IngredientNotFoundException;
import com.bolous.exceptions.RecipeNotFoundException;
import com.bolous.exceptions.UnitOfMeasureNotFoundException;
import com.bolous.repositories.IngredientRepository;
import com.bolous.repositories.RecipeRepository;
import com.bolous.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            throw new RecipeNotFoundException("recipe id not found id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!optionalIngredientCommand.isPresent()){
            throw new IngredientNotFoundException("Ingredient id not found: " + ingredientId);
        }

        return optionalIngredientCommand.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        }
        else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();

                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new UnitOfMeasureNotFoundException())); //todo address this
            }
            else{
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUnitOfMeasure().getId()))
                    .findFirst();


            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long id) {

        // Get teh recipe to remove the ingredient
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();

            // Get the ingredient that matches the passed in id
            Optional <Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(id))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                // Remove the ingredient from the recipe and save the update
                Ingredient ingredientToDelete = ingredientOptional.get();
                recipe.getIngredients().remove(ingredientOptional.get());
                ingredientToDelete.setRecipe(null);
                recipeRepository.save(recipe);

                // Delete the ingredient
                //ingredientRepository.deleteById(id);
            }

        }




    }
}
