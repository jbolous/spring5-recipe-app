package com.bolous.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryTest {

    private Category category;
    private static final String DESCRIPTION = "My Category";
    private static final Long ID = 1L;

    @Before
    public void setup(){
        category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);
        category.getRecipes().add(new Recipe());
    }

    @Test
    public void getId() {

        assertEquals(ID, category.getId());
    }

    @Test
    public void getDescription() {
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void getRecipes() {

        assertNotNull(category.getRecipes());
        assertEquals(1, category.getRecipes().size());
    }
}