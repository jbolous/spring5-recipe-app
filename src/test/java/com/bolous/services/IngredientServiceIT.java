package com.bolous.services;

import com.bolous.commands.IngredientCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientServiceIT {

    @Autowired
    private IngredientService ingredientService;

    @Transactional
    @Test//(expected = IngredientNotFoundException.class)
    public void deleteByRecipeIdAndIngredientID() {

        Long recipeId = 1L;
        Long ingredientId = 2L;

        // when
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        // then this should throw an exception
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        System.out.println(ingredientCommand);
    }
}