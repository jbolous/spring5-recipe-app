package com.bolous.services;

import com.bolous.commands.UnitOfMeasureCommand;
import com.bolous.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.bolous.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {

        Set<UnitOfMeasureCommand> unitOfMeasureCommand = new HashSet<>();

        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> unitOfMeasureCommand.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));

        return unitOfMeasureCommand;
    }
}
