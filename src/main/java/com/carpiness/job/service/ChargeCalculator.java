package com.carpiness.job.service;

import java.util.List;

import com.carpiness.job.domain.Job;

/**
 * Customer charge calculator.
 */
public interface ChargeCalculator {

    /**
     * Calculate the total charge to a customer.
     *
     * @param job the job with print items
     * @return total bill lines
     */
    List<String> calc(Job job);

}
