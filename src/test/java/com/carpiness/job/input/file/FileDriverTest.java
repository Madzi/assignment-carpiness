package com.carpiness.job.input.file;

import com.carpiness.job.config.BeanConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class FileDriverTest {

    private FileDriver fileDriver = BeanConfig.INSTANCE.getFileDriver();

    @Test
    public void testLoadFile() {
        // Given
        List<String> result = new ArrayList<>();

        // When
        Iterable<String> lines = fileDriver.load("src/test/resources/job1.in");
        lines.forEach(result::add);

        // Then
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void testLoadMissingFile() {
        // Given

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // When
            Iterable<String> lines = fileDriver.load("not-exists-file.in");
        });
    }

    @Test
    public void testSaveFile() {
        // Given
        List<String> expected = asList(
                "The lines",
                "",
                "used for test"
        );

        // When
        fileDriver.save("temp-file.out", expected);

        // Then
        List<String> actual;
        Path path = Paths.get("temp-file.out");
        try {
            actual = Files.readAllLines(path);
            Files.delete(path);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable create or delete file", ex);
        }
        Assertions.assertLinesMatch(expected, actual);
    }

}
