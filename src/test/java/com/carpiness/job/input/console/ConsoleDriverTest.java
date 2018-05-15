package com.carpiness.job.input.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ConsoleDriverTest {

    @Test
    public void testInputEmptyLines() {
        // Given
        InputStream  inputStream = new ByteArrayInputStream("\n\n".getBytes());

        ConsoleDriver consoleDriver = new ConsoleDriverImpl(inputStream, null);

        // When
        List<String> lines = new ArrayList<>();
        consoleDriver.load().forEach(lines::add);

        // Then
        Assertions.assertEquals(0, lines.size());
    }

    @Test
    public void testInputMultiLines() {
        // Given
        InputStream  inputStream = new ByteArrayInputStream("first line\nsecond line\n\nlast line".getBytes());

        ConsoleDriver consoleDriver = new ConsoleDriverImpl(inputStream, null);

        // When
        List<String> lines = new ArrayList<>();
        consoleDriver.load().forEach(lines::add);

        // Then
        Assertions.assertEquals(2, lines.size());
        System.setIn(System.in);
    }

    @Test
    public void testOutputEmptyLines() {
        // Given
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ConsoleDriver consoleDriver = new ConsoleDriverImpl(null, outputStream);

        List<String> jobResult = Collections.emptyList();

        // When
        consoleDriver.save(jobResult);

        // Then
        Assertions.assertEquals(jobResult.stream().collect(Collectors.joining("\n")), outputStream.toString());
    }

    @Test
    public void testOutputMultiLines() {
        // Given
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ConsoleDriver consoleDriver = new ConsoleDriverImpl(null, outputStream);

        List<String> jobResult = asList(
                "first line",
                "second line",
                "third line",
                ""
        );

        // When
        consoleDriver.save(jobResult);

        // Then
        Assertions.assertEquals(jobResult.stream().collect(Collectors.joining("\n")) + "\n", outputStream.toString());
    }

}
