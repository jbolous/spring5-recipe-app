package com.bolous.services;

import com.bolous.domain.Recipe;
import com.bolous.exceptions.RecipeNotFoundException;
import com.bolous.repositories.RecipeRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecipeServiceImpl.class);
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}
