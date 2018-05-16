package com.carpiness.job;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class AppTest {

    @Test
    public void testApplication() {
        // Given
        InputStream  inputStream  = new ByteArrayInputStream("".getBytes());
        PrintStream outputStream = new PrintStream(new ByteArrayOutputStream());

        System.setIn(inputStream);
        System.setOut(outputStream);
        App app = new App();

        // When
        app.processParams();

        // Then
        System.setIn(System.in);
        System.setOut(System.out);
        System.out.println(outputStream.toString());
    }

}
