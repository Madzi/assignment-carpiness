package com.carpiness.job.input;

import com.carpiness.job.domain.Job;

import java.util.List;

public interface JobLoader {
    Job load(Iterable<String> strings);
}
