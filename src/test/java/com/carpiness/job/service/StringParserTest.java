package com.carpiness.job.service;

import com.carpiness.job.config.BeanConfig;
import com.carpiness.job.domain.PrintItem;
import com.carpiness.job.domain.Job;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class StringParserTest {

    private StringParser loader = BeanConfig.INSTANCE.getJobLoader();

    @Test
    public void testEmptyInput() {
        // Given
        int expectedSize = 0;
        List<String> lines = Collections.emptyList();

        // When
        Job job = loader.load(lines);

        // Then
        int actualSize = job.getPrintItems().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testParsing() {
        // Given
        int expectedSize = 2;
        List<String> inputs = asList(
                "extra-margin",
                "envelopes 520.00",
                "letterhead 1983.37 exempt"
        );

        // When
        Job job = loader.load(inputs);

        // Then
        List<PrintItem> items = job.getPrintItems();
        int actualSize = items.size();
        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals("envelopes", items.get(0).getName());
        Assertions.assertEquals("letterhead", items.get(1).getName());
    }

    @Test
    public void testException() {
        // Given
        List<String> inputs = asList(
                "envelopes",
                "extra-margin",
                "letterhead 1983.37 exempt"
        );

        // When
        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Job job = loader.load(inputs);
        });
    }

}
