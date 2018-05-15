package com.carpiness.job.input;

import com.carpiness.job.domain.Job;
import com.carpiness.job.input.file.FileDriver;
import com.carpiness.job.service.Calculator;

import java.nio.charset.Charset;
import java.util.List;

public class FileProcessorImpl implements FileProcessor {

    private static final String INPUT_EXTENSION = ".in";
    private static final String OUTPUT_EXTENSION = ".out";

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private FileDriver fileDriver;
    private JobLoader  loader;
    private Calculator calculator;

    public FileProcessorImpl(FileDriver fileDriver, JobLoader loader, Calculator calculator) {
        this.fileDriver = fileDriver;
        this.calculator = calculator;
        this.loader = loader;
    }

    @Override
    public void process(String fileName) {
        Iterable<String> lines = fileDriver.load(fileName + INPUT_EXTENSION);
        Job job = loader.load(lines);
        List<String> result = calculator.calc(job);
        fileDriver.save(fileName + OUTPUT_EXTENSION, lines);
    }

}