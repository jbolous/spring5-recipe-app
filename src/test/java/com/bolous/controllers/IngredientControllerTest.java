package com.bolous.controllers;

import com.bolous.commands.IngredientCommand;
import com.bolous.commands.RecipeCommand;
import com.bolous.services.IngredientService;
import com.bolous.services.RecipeService;
import com.bolous.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientController controller;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception{
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        // when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        // then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception{

        // given
        IngredientCommand ingredientCommand = new IngredientCommand();

        // when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        // then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

        // then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }


    @Test
    public void deleteRecipeIngredient() throws Exception {

        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1"));

        verify(ingredientService, times(1)).deleteByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}