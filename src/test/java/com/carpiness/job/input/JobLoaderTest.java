package com.carpiness.job.input;

import com.carpiness.job.BeanRepo;
import com.carpiness.job.config.AppState;
import com.carpiness.job.domain.Item;
import com.carpiness.job.domain.Job;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import static java.util.Arrays.asList;

public class JobLoaderTest {

    private JobLoader loader = BeanRepo.INSTANCE.getJobLoader();

    @Test
    public void testEmptyInput() {
        // Given
        int expectedSize = 0;
        List<String> lines = Collections.emptyList();

        // When
        Job job = loader.load(lines);

        // Then
        int actualSize = job.getItems().size();
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
        List<Item> items = job.getItems();
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
