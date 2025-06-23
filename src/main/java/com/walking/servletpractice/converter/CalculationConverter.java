package com.walking.servletpractice.converter;

import com.walking.servletpractice.model.Calculation;
import com.walking.servletpractice.model.CalculationData;
import com.walking.servletpractice.model.dto.CalculationDto;

import java.util.stream.Collectors;

/**
 * Преобразует объект бизнес-логики {@code Calculation} в dto-объект {@code CalculationDto},
 * для отправки пользователю.
 */
public class CalculationConverter implements Converter<Calculation, CalculationDto> {
    @Override
    public CalculationDto convert(Calculation calculation) {
        var calculationDto = new CalculationDto();

        calculationDto.setType(calculation.getType()
                                          .getValue());

        calculationDto.setData(calculation.getData()
                                          .stream()
                                          .map(CalculationData::toString)
                                          .collect(Collectors.toList()));

        calculationDto.setResult(calculation.getResult()
                                            .toString());

        return calculationDto;
    }
}
