package com.carpiness.job.input;

/**
 * Process single file with job.
 */
public interface FileProcessor {

    /**
     * Process single file.
     *
     * @param fileName the file name without extension. The full file name will be {@code <fileName>.in}
     */
    void process(String fileName);

}
