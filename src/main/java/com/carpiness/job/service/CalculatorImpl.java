package com.carpiness.job.service;

import com.carpiness.job.domain.Item;
import com.carpiness.job.domain.Job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.math.BigDecimal.ZERO;

public class CalculatorImpl implements Calculator {

    private BigDecimal taxRate;
    private BigDecimal margin;
    private BigDecimal extraMargin;

    public CalculatorImpl(BigDecimal taxRate, BigDecimal margin, BigDecimal extraMargin) {
        this.taxRate = taxRate;
        this.margin = margin;
        this.extraMargin = extraMargin;
    }

    @Override
    public List<String> calc(Job job) {
        List<String> lines = new ArrayList<>();
        List<BigDecimal> incoming = new ArrayList<>();
        incoming.add(new BigDecimal(0.0));
        job.getItems().forEach(item -> {
            BigDecimal price = getPrice(item);
            lines.add(item.getName() + ": $" + price.setScale(2, ROUND_HALF_DOWN));
            incoming.add(price);
            incoming.add(getMargins(item));
            if (job.isExtra()) {
                incoming.add(getExtraMargin(item));
            }
        });
        lines.add("total: $" + incoming.stream().reduce(BigDecimal::add).get().setScale(2, ROUND_DOWN));
        return lines;
    }

    private BigDecimal getPrice(Item item) {
        return item.getCost().add(item.isExempt() ? ZERO : item.getCost().multiply(taxRate));
    }

    private BigDecimal getMargins(Item item) {
        return item.getCost().multiply(margin);
    }

    private BigDecimal getExtraMargin(Item item) {
        return item.getCost().multiply(extraMargin);
    }
}
