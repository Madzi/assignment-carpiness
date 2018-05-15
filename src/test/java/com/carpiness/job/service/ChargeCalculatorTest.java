package com.carpiness.job.service;

import java.util.List;

import com.carpiness.job.domain.PrintItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.carpiness.job.config.BeanConfig;
import com.carpiness.job.domain.Job;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ChargeCalculatorTest {

    private ChargeCalculator calculator = BeanConfig.INSTANCE.getCalculator();

    @Test
    public void testJobWithoutItems() {
        // Given
        Job emptyJob = new Job(emptyList());
        List<String> expected = asList(
                "total: $0.00"
        );

        // When
        List<String> actual = calculator.calc(emptyJob);

        // Then
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    public void testJobWithOneItem() {
        // Given
        Job oneItemJob = new Job(
                new PrintItem("t-shirts", 294.04)
        );
        List<String> expected = asList(
                "t-shirts: $314.62",
                "total: $346.96"
        );

        // When
        List<String> actual = calculator.calc(oneItemJob);

        // Then
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    public void testJobWithExtraMargin() {
        // Given
        Job extraMarginJob = new Job(true,
                new PrintItem("envelopes", 520.00),
                new PrintItem("letterhead", 1983.37, true)
        );
        List<String> expected = asList(
                "envelopes: $556.40",
                "letterhead: $1983.37",
                "total: $2940.30"
        );

        // When
        List<String> actual = calculator.calc(extraMarginJob);

        // Then
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    public void testJobWithExtraMarginAndAllTaxFree() {
        // Given
        Job extraMarginTaxFreeJob = new Job(true,
                new PrintItem("frisbees", 19385.38, true),
                new PrintItem("yo-yos", 1829.00, true)
        );
        List<String> expected = asList(
                "frisbees: $19385.38",
                "yo-yos: $1829.00",
                "total: $24608.68"
        );

        // When
        List<String> actual = calculator.calc(extraMarginTaxFreeJob);

        // Then
        Assertions.assertLinesMatch(expected, actual);
    }

}
