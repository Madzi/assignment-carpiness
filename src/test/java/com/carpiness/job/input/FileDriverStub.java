package com.carpiness.job.input;

import com.carpiness.job.input.file.FileDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDriverStub implements FileDriver {

    private Map<String, List<String>> fileData;

    public FileDriverStub(Map<String, List<String>> initialData) {
        fileData = initialData;
    }

    public FileDriverStub() {
        this(new HashMap<>());
    }

    @Override
    public Iterable<String> load(String fileName) {
        return fileData.get(fileName);
    }

    @Override
    public void save(String fileName, Iterable<String> lines) {
        fileData.put(fileName, toList(lines));
    }

    private List<String> toList(Iterable<String> lines) {
        List<String> list = new ArrayList<>();
        lines.forEach(list::add);
        return list;
    }

}
