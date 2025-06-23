package com.walking.servletpractice.service;

import com.walking.servletpractice.model.Calculation;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранит историю вычислений и обеспечивает к ней доступ.
 */
public class CalculationStorageService {
    private final List<Calculation> storage = new ArrayList<>();

    public List<Calculation> getAll() {
        return new ArrayList<>(storage);
    }

    public void save(Calculation calculation) {
        storage.add(calculation);
    }
}
