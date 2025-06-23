package com.walking.servletpractice.model;

import java.util.NoSuchElementException;

/**
 * Ограничивает список типов арифметических операций, которые могут быть обработаны.
 */
public enum CalculationType {
    ADDITION("addition"),
    SUBTRACTION("subtraction"),
    MULTIPLICATION("multiplication"),
    DIVISION("division");

    private final String value;

    CalculationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CalculationType getByValue(String value) {
        for (CalculationType type : CalculationType.values()) {
            if (type.getValue()
                    .equals(value)) {
                return type;
            }
        }

        throw new NoSuchElementException(
                "Cannot get CalculationType from value: '%s'".formatted(value));
    }
}
