package com.carpiness.job.config;

import com.carpiness.job.input.FileProcessor;
import com.carpiness.job.input.FileProcessorImpl;
import com.carpiness.job.input.console.ConsoleDriver;
import com.carpiness.job.input.console.ConsoleDriverImpl;
import com.carpiness.job.input.file.FileDriver;
import com.carpiness.job.input.file.TxtFileDriver;
import com.carpiness.job.service.ChargeCalculator;
import com.carpiness.job.service.ChargeCalculatorImpl;
import com.carpiness.job.service.StringParser;
import com.carpiness.job.service.StringParserImpl;

/**
 * Simple DI realisation. Singleton.
 */
public enum BeanConfig {
    INSTANCE;

    private ConsoleDriver    consoleDriver;
    private FileDriver       fileDriver;
    private ChargeCalculator chargeCalculator;
    private StringParser     stringParser;
    private FileProcessor    fileProcessor;

    BeanConfig() {
        consoleDriver = new ConsoleDriverImpl(System.in, System.out);
        fileDriver    = new TxtFileDriver();
        stringParser = new StringParserImpl();
        chargeCalculator = new ChargeCalculatorImpl(
                ApplicationContext.INSTANCE.getTaxRate(),
                ApplicationContext.INSTANCE.getMargin(),
                ApplicationContext.INSTANCE.getExtraMargin()
        );
        fileProcessor = new FileProcessorImpl(fileDriver, stringParser, chargeCalculator);
    }

    public ConsoleDriver getConsoleDriver() {
        return consoleDriver;
    }

    public FileDriver getFileDriver() {
        return fileDriver;
    }

    public ChargeCalculator getChargeCalculator() {
        return chargeCalculator;
    }

    public StringParser getStringParser() {
        return stringParser;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

}
