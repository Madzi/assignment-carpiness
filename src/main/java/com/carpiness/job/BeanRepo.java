package com.carpiness.job;

import com.carpiness.job.config.AppState;
import com.carpiness.job.input.JobLoader;
import com.carpiness.job.input.JobLoaderImpl;
import com.carpiness.job.service.Calculator;
import com.carpiness.job.service.CalculatorImpl;

public enum BeanRepo {
    INSTANCE;

    private Calculator calculator;
    private JobLoader jobLoader;

    BeanRepo() {
        jobLoader = new JobLoaderImpl();
        calculator = new CalculatorImpl(
                AppState.INSTANCE.getTaxRate(),
                AppState.INSTANCE.getMargin(),
                AppState.INSTANCE.getExtraMargin()
        );
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public JobLoader getJobLoader() {
        return jobLoader;
    }

}
