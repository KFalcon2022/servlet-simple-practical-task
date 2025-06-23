package com.walking.servletpractice.model;

/**
 * Объект-значение инкапсулирующий значение результата вычисления.
 */
public class CalculationResult {
    private final double value;

    public CalculationResult(double value) {
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
