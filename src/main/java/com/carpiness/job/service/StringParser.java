package com.carpiness.job.service;

import com.carpiness.job.domain.Job;

import java.util.List;

public interface StringParser {
    Job load(Iterable<String> strings);
}
