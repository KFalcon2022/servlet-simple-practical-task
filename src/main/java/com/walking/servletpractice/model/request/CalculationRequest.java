package com.walking.servletpractice.model.request;

import com.walking.servletpractice.model.CalculationData;
import com.walking.servletpractice.model.CalculationType;

import java.util.List;

/**
 * Dto-объект, содержащий данные для вычислений, полученные от пользователя.
 */
public class CalculationRequest {
    private CalculationType type;
    private List<CalculationData> data;

    public CalculationType getType() {
        return type;
    }

    public void setType(CalculationType type) {
        this.type = type;
    }

    public List<CalculationData> getData() {
        return data;
    }

    public void setData(List<CalculationData> data) {
        this.data = data;
    }
}
