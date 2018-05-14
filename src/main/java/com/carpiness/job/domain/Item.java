package com.carpiness.job.domain;

import java.math.BigDecimal;

public class Item {

    private String name;
    private BigDecimal cost;
    private boolean exempt;

    public Item(String name, BigDecimal cost, boolean exempt) {
        this.name = name;
        this.cost = cost;
        this.exempt = exempt;
    }

    public Item(String name, double cost, boolean exempt) {
        this(name, new BigDecimal(cost), exempt);
    }

    public Item(String name, BigDecimal cost) {
        this(name, cost, false);
    }

    public Item(String name, double cost) {
        this(name, new BigDecimal(cost));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public boolean isExempt() {
        return exempt;
    }

}
