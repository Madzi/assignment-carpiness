package com.carpiness.job;

import java.math.BigDecimal;

import com.carpiness.job.service.Calculator;
import com.carpiness.job.service.CalculatorImpl;

public enum BeanRepo {
    INSTANCE;

    private Calculator calculator;

    BeanRepo() {
        calculator = new CalculatorImpl(new BigDecimal("0.07"), new BigDecimal("0.11"), new BigDecimal("0.05"));
    }

    public Calculator getCalculator() {
        return calculator;
    }

}
