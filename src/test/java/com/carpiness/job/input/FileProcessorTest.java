package com.carpiness.job.input;

import com.carpiness.job.config.BeanConfig;
import com.carpiness.job.input.file.FileDriver;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class FileProcessorTest {

    private BeanConfig config = BeanConfig.INSTANCE;

    @Test
    public void testProcessingCorrectFile() {
        // Given
        Map<String, List<String>> initData = new HashMap<>();
        initData.put("job1.in", asList(
                "extra-margin",
                "envelopes 520.00",
                "letterhead 1983.37 exempt"
        ));
        FileDriver fileDriver = new FileDriverStub(initData);
        FileProcessor fileProcessor = new FileProcessorImpl(fileDriver, config.getStringParser(), config.getChargeCalculator());

        // When
        fileProcessor.process("job1");

        // Then
        List<String> actualLines = new ArrayList<>();
        fileDriver.load("job1.out").forEach(actualLines::add);
        List<String> expectedLines = asList(
                "envelopes: $556.40",
                "letterhead: $1983.37",
                "total: $2940.30"
        );
        Assertions.assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    public void testProcessingIncorrectFile() {
        // Given
        Map<String, List<String>> initData = new HashMap<>();
        initData.put("job1.in", asList(
                "envelopes 520.00",
                "extra-margin",
                "letterhead 1983.37 exempt"
        ));
        FileDriver fileDriver = new FileDriverStub(initData);
        FileProcessor fileProcessor = new FileProcessorImpl(fileDriver, config.getStringParser(), config.getChargeCalculator());

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // When
            fileProcessor.process("job1");
        });
    }

    @Test
    public void testProcessingUnExistingFile() {
        // Given
        FileProcessor fileProcessor = config.getFileProcessor();

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // When
            fileProcessor.process("un-existing-file");
        });
    }

}
