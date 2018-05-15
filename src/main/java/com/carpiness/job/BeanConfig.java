package com.carpiness.job;

import com.carpiness.job.config.AppState;
import com.carpiness.job.input.FileProcessor;
import com.carpiness.job.input.FileProcessorImpl;
import com.carpiness.job.input.JobLoader;
import com.carpiness.job.input.JobLoaderImpl;
import com.carpiness.job.input.console.ConsoleDriver;
import com.carpiness.job.input.console.ConsoleDriverImpl;
import com.carpiness.job.input.file.FileDriver;
import com.carpiness.job.input.file.TxtFileDriver;
import com.carpiness.job.service.Calculator;
import com.carpiness.job.service.CalculatorImpl;

/**
 * Simple DI realisation. Singleton.
 */
public enum BeanConfig {
    INSTANCE;

    private ConsoleDriver consoleDriver;
    private FileDriver    fileDriver;
    private Calculator    calculator;
    private JobLoader     jobLoader;
    private FileProcessor fileProcessor;

    BeanConfig() {
        consoleDriver = new ConsoleDriverImpl(System.in, System.out);
        fileDriver    = new TxtFileDriver();
        jobLoader     = new JobLoaderImpl();
        calculator    = new CalculatorImpl(
                AppState.INSTANCE.getTaxRate(),
                AppState.INSTANCE.getMargin(),
                AppState.INSTANCE.getExtraMargin()
        );
        fileProcessor = new FileProcessorImpl(fileDriver, jobLoader, calculator);
    }

    public ConsoleDriver getConsoleDriver() {
        return consoleDriver;
    }

    public FileDriver getFileDriver() {
        return fileDriver;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public JobLoader getJobLoader() {
        return jobLoader;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

}
