package com.walking.servletpractice.model;

import java.util.List;

/**
 * Объект бизнес-логики отражающий конкретное вычисление. Инкапсулирует данные
 * о типе вычисления, данных для вычисления и результате вычисления.
 */
public class Calculation {
    private final CalculationType type;
    private final List<CalculationData> data;
    private final CalculationResult result;

    public Calculation(CalculationType type, List<CalculationData> data, CalculationResult result) {
        this.type = type;
        this.data = data;
        this.result = result;
    }

    public CalculationType getType() {
        return type;
    }

    public List<CalculationData> getData() {
        return data;
    }

    public CalculationResult getResult() {
        return result;
    }
}
