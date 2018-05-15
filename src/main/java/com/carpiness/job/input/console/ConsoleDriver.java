package com.carpiness.job.input.console;

/**
 * Interface for read/write job from console (user interaction).
 */
public interface ConsoleDriver {

    /**
     * Input lines entered by user via console.
     *
     * @return the entered string lines
     */
    Iterable<String> load();

    /**
     * Output lines to console.
     *
     * @param lines the string lines
     */
    void save(Iterable<String> lines);

}
