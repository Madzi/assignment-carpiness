package com.carpiness.job.input;

import com.carpiness.job.BeanRepo;
import com.carpiness.job.config.AppState;
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
        int actualSize = job.getItems().size();

        // Then
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testParsing() {
        List<String> inputs = asList(
                "extra-margin",
                "envelopes 520.00",
                "letterhead 1983.37 exempt"
        );
        StringTokenizer tokenizer = new StringTokenizer("The long  long   long string");
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
//        asList("The long  long   long string".split(" ")).forEach(System.out::println);
    }

}
