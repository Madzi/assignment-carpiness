package com.carpiness.job.input.file;

/**
 * Common interface to read/write files with different formats.
 */
public interface FileDriver {

    /**
     * Read strings from file.
     *
     * @param fileName the full file name
     * @return file strings
     */
    Iterable<String> load(String fileName);

    /**
     * Write strings to file.
     *
     * @param fileName the full file name
     * @param lines    strings to write
     */
    void save(String fileName, Iterable<String> lines);

}
