package com.bolous.bootstrap;

import com.bolous.domain.*;
import com.bolous.exceptions.CategoryException;
import com.bolous.exceptions.UnitOfMeasureException;
import com.bolous.repositories.CategoryRepository;
import com.bolous.repositories.RecipeRepository;
import com.bolous.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        log.debug("In Constructor...");
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("In application event...");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        log.debug("Building the recipes...");

        // Get Units of Measure
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        Optional<UnitOfMeasure> tablespoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tablespoonUomOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaspoonOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupUomOptional.isPresent()){
            throw new UnitOfMeasureException();
        }

        // Get Optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaspoonOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        // Get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()) {
            throw new CategoryException();
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");

        if(!italianCategoryOptional.isPresent()){
            throw new CategoryException();
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new CategoryException();
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();

        recipes.add(getGuacamoleRecipe(eachUom, tablespoonUom, teaspoonUom, dashUom, americanCategory, mexicanCategory));
        recipes.add(getTacosRecipe(tablespoonUom, teaspoonUom, eachUom, cupUom, pintUom, mexicanCategory, americanCategory));

        return recipes;
    }

    /**
     * Build guacamole recipe
     * @param eachUom
     * @param tablespoonUom
     * @param teaspoonUom
     * @param dashUom
     * @param americanCategory
     * @param mexicanCategory
     * @return
     */
    private Recipe getGuacamoleRecipe(UnitOfMeasure eachUom, UnitOfMeasure tablespoonUom, UnitOfMeasure teaspoonUom, UnitOfMeasure dashUom, Category americanCategory, Category mexicanCategory) {

        log.debug("Build yummy guacamole recipe");

        // Yummy Guacamole
        Recipe recipe = new Recipe();

        recipe.setDescription("Perfect Guacamole");
        recipe.setPrepTime(10);
        recipe.setCookTime(0);
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n");

        // Set Ingredients
        recipe.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), eachUom, recipe));
        recipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(.5), teaspoonUom, recipe));
        recipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", BigDecimal.ONE, tablespoonUom, recipe));
        recipe.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoonUom, recipe));
        recipe.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", BigDecimal.ONE, eachUom, recipe));
        recipe.getIngredients().add(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUom, recipe));
        recipe.getIngredients().add(new Ingredient("freshly grated black pepper", BigDecimal.ONE, dashUom, recipe));
        recipe.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), eachUom, recipe));

        // Set Categories
        recipe.getCategories().add(americanCategory);
        recipe.getCategories().add(mexicanCategory);

        recipe.setServings(2);
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setSource("Simply Recipes");

        Notes notes = new Notes("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).\n" +
                "\n" +
                "MAKING GUACAMOLE IS EASY\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n" +
                "\n" +
                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n" +
                "\n" +
                "GUACAMOLE TIP: USE RIPE AVOCADOS\n" +
                "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.\n" +
                "\n" +
                "WATCH HOW TO MAKE GUACAMOLE\n" +
                "Guacamole\n" +
                "Pause\n" +
                "Unmute\n" +
                " \n" +
                "Fullscreen\n");

        recipe.setNotes(notes);

        return recipe;
    }


    private Recipe getTacosRecipe(UnitOfMeasure tablespoonUom, UnitOfMeasure teaspoonUom, UnitOfMeasure eachUom, UnitOfMeasure cupUom, UnitOfMeasure pintUom, Category mexicanCategory, Category americanCategory){

        log.debug("Build Spicy Chicken Tacos recipe...");

        Recipe recipe = new Recipe();

        recipe.setDescription("Spicy Grilled Chicken Tacos");
        recipe.setPrepTime(20);
        recipe.setCookTime(15);

        recipe.setDifficulty(Difficulty.MODERATE);

        recipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        // Chicken Ingredients
        recipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tablespoonUom));
        recipe.addIngredient(new Ingredient("dried oregano", BigDecimal.ONE, teaspoonUom));
        recipe.addIngredient(new Ingredient("dried cumin", BigDecimal.ONE, teaspoonUom));
        recipe.addIngredient(new Ingredient("sugar", BigDecimal.ONE, teaspoonUom));
        recipe.addIngredient(new Ingredient("salt", new BigDecimal(.5), teaspoonUom));
        recipe.addIngredient(new Ingredient("clove garlic, finely chopped", BigDecimal.ONE, eachUom));
        recipe.addIngredient(new Ingredient("finely grated orange zest", BigDecimal.ONE, tablespoonUom));
        recipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUom));
        recipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tablespoonUom));
        recipe.addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal(4), eachUom));

        // Serving Ingredients
        recipe.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8), eachUom));
        recipe.addIngredient(new Ingredient("packed baby arugula (3 ounces)", new BigDecimal(3), cupUom));
        recipe.addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2), eachUom));
        recipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        recipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(.5), pintUom));
        recipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(.25), eachUom));
        recipe.addIngredient(new Ingredient("Roughly chopped cilantro", BigDecimal.ONE, eachUom));
        recipe.addIngredient(new Ingredient("sour cream thinned with 1/4 cup milk", new BigDecimal(.5), cupUom));
        recipe.addIngredient(new Ingredient("lime, cut into wedges", BigDecimal.ONE, eachUom));

        recipe.getCategories().add(mexicanCategory);
        recipe.getCategories().add(americanCategory);

        recipe.setServings(4);
        recipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        recipe.setSource("Simply Recipes");

        Notes note = new Notes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "\n" +
                "\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n" +
                "\n" +
                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n" +
                "\n" +
                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +
                "\n" +
                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!\n" +
                "\n");

        recipe.setNotes(note);

        return recipe;
    }
}
