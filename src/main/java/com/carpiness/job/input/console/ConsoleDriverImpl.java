package com.carpiness.job.input.console;

import com.carpiness.job.config.AppState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleDriverImpl implements ConsoleDriver {

    private InputStream  inputStream;
    private OutputStream outputStream;

    public ConsoleDriverImpl(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public Iterable<String> load() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = reader.readLine();
            while (!line.isEmpty()) {
                lines.add(line);
                line = reader.readLine();
            }
            return lines;
        } catch (IOException ex) {
            AppState.INSTANCE.checkFailFast("Unable read from console");
        }
        return Collections.emptyList();
    }

    @Override
    public void save(Iterable<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            lines.forEach(line -> writeLine(writer, line));
        } catch (IOException ex) {
            AppState.INSTANCE.checkFailFast("Unable write to console");
        }
    }

    private void writeLine(Writer writer, String line) {
        try {
            writer.write(line + "\n");
        } catch (IOException ex) {
            AppState.INSTANCE.checkFailFast("Problem with writing string: " + line);
        }
    }

}
