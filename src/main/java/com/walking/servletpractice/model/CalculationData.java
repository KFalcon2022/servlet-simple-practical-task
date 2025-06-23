package com.walking.servletpractice.model;

/**
 * Объект-значение инкапсулирующий данные, используемые для вычислений.
 */
public class CalculationData {
    private final double value;

    public CalculationData(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
