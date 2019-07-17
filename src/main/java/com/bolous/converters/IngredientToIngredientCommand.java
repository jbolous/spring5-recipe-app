package com.bolous.converters;

import com.bolous.commands.IngredientCommand;
import com.bolous.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter) {
        this.uomCommandConverter = uomCommandConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {

        if(source == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());
        command.setAmount(source.getAmount());
        command.setDescription(source.getDescription());
        command.setUnitOfMeasure(uomCommandConverter.convert(source.getUom()));

        return command;
    }
}
