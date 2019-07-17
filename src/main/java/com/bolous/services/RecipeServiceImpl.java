package com.bolous.services;

import com.bolous.commands.RecipeCommand;
import com.bolous.converters.RecipeCommandToRecipe;
import com.bolous.converters.RecipeToRecipeCommand;
import com.bolous.domain.Recipe;
import com.bolous.exceptions.RecipeNotFoundException;
import com.bolous.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {


    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting recipe from services");
        
        Set<Recipe> recipes = new HashSet<>();
        this.recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);

        if(!recipeOptional.isPresent()){
            throw new RecipeNotFoundException();
        }

        return recipeOptional.get();
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {

        Recipe recipe = recipeCommandToRecipe.convert(command);
        recipe = recipeRepository.save(recipe);

        log.debug("Saved RecipeID: " + recipe.getId());
        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);

        log.debug("Deleted ID: " + id);
    }
}
