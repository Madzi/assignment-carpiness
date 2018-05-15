package com.carpiness.job.service;

import com.carpiness.job.domain.Job;

/**
 * Job scanner/parser.
 */
public interface StringParser {

    /**
     * Scan/parse input lines and generate Job model.
     *
     * @param strings the input lines
     * @return the job model
     */
    Job load(Iterable<String> strings);

}
