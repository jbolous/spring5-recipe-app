package com.bolous.converters;

import com.bolous.commands.CategoryCommand;
import com.bolous.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand converter;

    private static final String DESCRIPTION = "Description";
    private static final Long ID_VALUE = Long.valueOf(1L);

    @Before
    public void setUp(){
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }


    @Test
    public void convert() {

        // Given
        Category category = new Category();
        category.setDescription(DESCRIPTION);
        category.setId(ID_VALUE);

        CategoryCommand categoryCommand = converter.convert(category);

        //Then
        assertNotNull(categoryCommand);
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}