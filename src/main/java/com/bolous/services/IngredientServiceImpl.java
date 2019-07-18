package com.bolous.services;

import com.bolous.commands.IngredientCommand;
import com.bolous.converters.IngredientToIngredientCommand;
import com.bolous.domain.Recipe;
import com.bolous.exceptions.IngredientNotFoundException;
import com.bolous.exceptions.RecipeNotFoundException;
import com.bolous.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientCommand,
                                 RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientCommand;
        this.recipeRepository = recipeRepository;
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
}
