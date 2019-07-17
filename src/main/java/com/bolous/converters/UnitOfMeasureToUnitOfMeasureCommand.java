package com.bolous.converters;

import com.bolous.commands.UnitOfMeasureCommand;
import com.bolous.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {

        if(source == null) {
            return null;
        }

        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setDescription(source.getDescription());
        command.setId(source.getId());

        return command;
    }
}
