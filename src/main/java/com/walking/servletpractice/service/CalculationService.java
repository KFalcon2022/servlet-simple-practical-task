package com.walking.servletpractice.service;

import com.walking.servletpractice.model.Calculation;
import com.walking.servletpractice.model.CalculationData;
import com.walking.servletpractice.model.CalculationResult;
import com.walking.servletpractice.model.CalculationType;

import java.util.List;

/**
 * Реализует бизнес-логику, связанную с вычислениями.
 */
public class CalculationService {
    /**
     * В зависимости от значения {@code CalculationType} и на основе данных из {@code CalculationData},
     * вычисляет значение для объекта {@code CalculationResult}.
     *
     * @param type на основе этого значения выбирает подходящий способ вычисления
     * @param data список данных для вычисления. Должен содержать не менее 2-х элементов
     * @return объект {@code Calculation}, содержащий соответствующие объекты {@code CalculationType},
     * {@code CalculationData} и {@code CalculationResult}
     * @throws IllegalArgumentException если в списке data, менее 2-х значений
     */
    public Calculation calculated(CalculationType type, List<CalculationData> data) {
        CalculationResult result = calculate(type, data);

        return new Calculation(type, data, result);
    }

    private CalculationResult calculate(CalculationType type, List<CalculationData> data) {
        return switch (type) {
            case ADDITION -> add(data);
            case SUBTRACTION -> subtract(data);
            case MULTIPLICATION -> multiply(data);
            case DIVISION -> divide(data);
        };
    }

    private CalculationResult add(List<CalculationData> calculationData) {
        var result = calculationData.stream()
                                    .mapToDouble(CalculationData::getValue)
                                    .reduce((a, b) -> a + b)
                                    .orElseThrow(() -> getIllegalException(calculationData.size()));

        return new CalculationResult(result);
    }

    private CalculationResult subtract(List<CalculationData> calculationData) {
        var result = calculationData.stream()
                                    .mapToDouble(CalculationData::getValue)
                                    .reduce((a, b) -> a - b)
                                    .orElseThrow(() -> getIllegalException(calculationData.size()));

        return new CalculationResult(result);
    }

    private CalculationResult multiply(List<CalculationData> calculationData) {
        var result = calculationData.stream()
                                    .mapToDouble(CalculationData::getValue)
                                    .reduce((a, b) -> a * b)
                                    .orElseThrow(() -> getIllegalException(calculationData.size()));

        return new CalculationResult(result);
    }

    private CalculationResult divide(List<CalculationData> calculationData) {
        var result = calculationData.stream()
                                    .mapToDouble(CalculationData::getValue)
                                    .reduce((a, b) -> a / b)
                                    .orElseThrow(() -> getIllegalException(calculationData.size()));

        return new CalculationResult(result);
    }

    private IllegalArgumentException getIllegalException(int actualSize) {
        return new IllegalArgumentException(
                "List of calculationData must contain at least 2 elements, but contains: '%s'".formatted(
                        actualSize));
    }
}
