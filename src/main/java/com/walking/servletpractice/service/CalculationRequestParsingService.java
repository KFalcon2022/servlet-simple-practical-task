package com.walking.servletpractice.service;

import com.walking.servletpractice.exception.ParsingException;
import com.walking.servletpractice.model.CalculationData;
import com.walking.servletpractice.model.CalculationType;
import com.walking.servletpractice.model.request.CalculationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Преобразует строку с данными в объект {@code CalculationRequest}, заполняя его поля
 * по определенным правилам. <p>
 * Строка должна содержать аргументы, разделенные амперсандом(&) в следующем порядке: <br>
 * 1) type - тип вычислений. Допустимые значения: <br>
 * &emsp; addition - сложение <br>
 * &emsp; subtraction - сложение <br>
 * &emsp; multiplication - сложение <br>
 * &emsp; division - сложение <br>
 * 2) data - данные для вычислений. Допустимые значения:
 * десятичные, целые или вещественные числа с фиксированным десятичным разделителем в виде точки(.).
 * Допустимы отрицательные числа с символом минус(-) перед ними. Допускается минимум два числа,
 * разделенных запятой(,). <p>
 * Пример допустимой строки: type=multiplication&data=-1,0,1,-2.2,0.0,2.2
 */
public class CalculationRequestParsingService {
    private static final String ARGUMENT_DELIMITER = "&";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final String VALUES_DELIMITER = ",";

    private static final String VALIDATION_REGEXP =
            "^type=\\w+&data=(-*\\d+(\\.\\d+)*,)+(-*\\d+(\\.\\d+)*)+$";

    /**
     * Преобразует строку, содержащую данные в определенном формате в объект {@code CalculationRequest},
     * заполняя его поля по определенным правилам.
     *
     * @param inputData строка с данными, для успешного преобразования должна соответствовать
     *                  шаблону: "^type=\w+&data=(-*\d+(\.\d+)*,)+(-*\d+(\.\d+)*)+$"
     * @return объект {@code CalculationRequest}, содержащий преобразованные данные для вычислений
     * @throws ParsingException если строка не соответствует шаблону или содержит данные
     *                          в неподходящем формате.
     */
    public CalculationRequest parse(String inputData) {
        try {
            validate(inputData);

            String[] arguments = inputData.split(ARGUMENT_DELIMITER);

            CalculationType type = parseType(arguments[0]);

            List<CalculationData> data = parseData(arguments[1]);

            var calculationRequest = new CalculationRequest();

            calculationRequest.setType(type);
            calculationRequest.setData(data);

            return calculationRequest;
        } catch (NoSuchElementException | ParsingException e) {
            throw new ParsingException(inputData, e);
        }
    }

    private void validate(String inputData) {
        if (!inputData.matches(VALIDATION_REGEXP)) {
            throw new ParsingException();
        }
    }

    private CalculationType parseType(String argument) {
        String value = argument.split(NAME_VALUE_DELIMITER)[1];

        return CalculationType.getByValue(value);
    }

    private List<CalculationData> parseData(String argument) {
        String data = argument.split(NAME_VALUE_DELIMITER)[1];

        String[] values = data.split(VALUES_DELIMITER);

        List<CalculationData> calculationDataList = new ArrayList<>();

        for (String value : values) {
            try {
                double doubleValue = Double.parseDouble(value);

                var calculationData = new CalculationData(doubleValue);

                calculationDataList.add(calculationData);
            } catch (NumberFormatException e) {
                throw new ParsingException(value, e);
            }
        }

        return calculationDataList;
    }
}
