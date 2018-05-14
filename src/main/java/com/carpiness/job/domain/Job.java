package com.carpiness.job.domain;

import java.util.List;

import static java.util.Arrays.asList;

public class Job {

    private List<Item> items;
    private boolean extra;

    public Job(boolean extra, List<Item> items) {
        this.extra = extra;
        this.items = items;
    }

    public Job(List<Item> items) {
        this(false, items);
    }

    public Job(boolean extra, Item... items) {
        this(extra, asList(items));
    }

    public Job(Item... items) {
        this(asList(items));
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isExtra() {
        return extra;
    }

}
