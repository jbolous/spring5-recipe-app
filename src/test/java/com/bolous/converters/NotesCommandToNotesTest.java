package com.bolous.converters;

import com.bolous.commands.NotesCommand;
import com.bolous.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    private static final Long ID_VALUE = Long.valueOf(1L);
    private static final String RECIPE_NOTES = "This recipe is amazing...";

    private NotesCommandToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullParameter() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {

        // Given
        NotesCommand command = new NotesCommand();
        command.setRecipeNotes(RECIPE_NOTES);
        command.setId(ID_VALUE);

        // When
        Notes notes = converter.convert(command);

        //Then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}