package com.walking.servletpractice.model.dto;

import java.util.List;
import java.util.StringJoiner;

/**
 * Dto-объект, содержащий данные вычисления, возвращаемые пользователю.
 */
public class CalculationDto {
    private static final String ARGUMENT_DELIMITER = "&";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final String VALUES_DELIMITER = ",";

    private String type;
    private List<String> data;
    private String result;

    public void setType(String type) {
        this.type = type;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Преобразует данные вычислений в строку вида: "type=value&data=value1,valueN&result=value"
     */
    @Override
    public String toString() {
        var argumentJoiner = new StringJoiner(ARGUMENT_DELIMITER);

        addType(argumentJoiner);
        addData(argumentJoiner);
        addResult(argumentJoiner);

        return argumentJoiner.toString();
    }

    private void addType(StringJoiner stringJoiner) {
        stringJoiner.add("type" + NAME_VALUE_DELIMITER + type);
    }

    private void addData(StringJoiner stringJoiner) {
        var valuesJoiner = new StringJoiner(VALUES_DELIMITER);

        for (String value : data) {
            valuesJoiner.add(value);
        }

        stringJoiner.add("data" + NAME_VALUE_DELIMITER + valuesJoiner);
    }

    private void addResult(StringJoiner stringJoiner) {
        stringJoiner.add("result" + NAME_VALUE_DELIMITER + result);
    }
}
