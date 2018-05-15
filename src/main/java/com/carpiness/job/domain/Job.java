package com.carpiness.job.domain;

import java.util.List;

import static java.util.Arrays.asList;

public class Job {

    private List<PrintItem> printItems;
    private boolean extra;

    public Job(boolean extra, List<PrintItem> printItems) {
        this.extra = extra;
        this.printItems = printItems;
    }

    public Job(List<PrintItem> printItems) {
        this(false, printItems);
    }

    public Job(boolean extra, PrintItem... printItems) {
        this(extra, asList(printItems));
    }

    public Job(PrintItem... printItems) {
        this(asList(printItems));
    }

    public List<PrintItem> getPrintItems() {
        return printItems;
    }

    public boolean isExtra() {
        return extra;
    }

}
