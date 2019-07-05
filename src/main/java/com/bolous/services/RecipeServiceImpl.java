package com.bolous.services;

import com.bolous.domain.Recipe;
import com.bolous.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting recipes from services");
        
        Set<Recipe> recipes = new HashSet<>();
        this.recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }
}
