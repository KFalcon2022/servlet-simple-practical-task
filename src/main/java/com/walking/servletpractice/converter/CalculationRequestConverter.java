package com.walking.servletpractice.converter;

import com.walking.servletpractice.model.Calculation;
import com.walking.servletpractice.model.request.CalculationRequest;
import com.walking.servletpractice.service.CalculationService;

/**
 * Преобразует dto-объект {@code CalculationRequest} в объект бизнес-логики {@code Calculation}.
 * Объект {@code Calculation} запрашивается у {@code CalculationService}, который отвечает за вычисления.
 */
public class CalculationRequestConverter implements Converter<CalculationRequest, Calculation> {
    private final CalculationService calculationService;

    public CalculationRequestConverter(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Override
    public Calculation convert(CalculationRequest source) {
        return calculationService.calculated(source.getType(), source.getData());
    }
}
