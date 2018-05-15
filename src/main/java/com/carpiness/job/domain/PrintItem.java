package com.carpiness.job.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.math.BigDecimal.ZERO;

public class PrintItem {

    private String name;
    private BigDecimal cost;
    private boolean exempt;

    public PrintItem(String name, BigDecimal cost, boolean exempt) {
        this.name = name;
        this.cost = cost;
        this.exempt = exempt;
    }

    public PrintItem(String name, double cost, boolean exempt) {
        this(name, new BigDecimal(cost), exempt);
    }

    public PrintItem(String name, BigDecimal cost) {
        this(name, cost, false);
    }

    public PrintItem(String name, double cost) {
        this(name, new BigDecimal(cost));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getFactoredCost(BigDecimal factor) {
        return cost.multiply(factor);
    }

    public BigDecimal getCostWithTax(BigDecimal taxRate) {
        return cost.add(exempt ? ZERO : cost.multiply(taxRate));
    }

    public boolean isExempt() {
        return exempt;
    }

    public String asString(BigDecimal taxRate) {
        return name + ": $" + getCostWithTax(taxRate).setScale(2, ROUND_HALF_DOWN);
    }

}
