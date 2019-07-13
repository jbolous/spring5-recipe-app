package com.bolous.controllers;

import com.bolous.services.RecipeService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(IndexController.class);
    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){

        log.debug("Getting index for recipe");
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
