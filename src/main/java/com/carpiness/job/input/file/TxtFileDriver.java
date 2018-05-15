package com.carpiness.job.input.file;

import com.carpiness.job.config.AppState;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxtFileDriver  implements FileDriver {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    public Iterable<String> load(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.collect(Collectors.toList());
        } catch (IOException ex) {
            AppState.INSTANCE.checkFailFast("Unable load file: " + fileName);
        }
        return Collections.emptyList();
    }

    @Override
    public void save(String fileName, Iterable<String> lines) {
        Path out = Paths.get(fileName);
        try {
            Files.write(out, lines, CHARSET);
        } catch (IOException ex) {
            AppState.INSTANCE.checkFailFast("Unable write result to file: " + fileName);
        }
    }

}
