package com.carpiness.job.service;

import com.carpiness.job.domain.PrintItem;
import com.carpiness.job.domain.Job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.math.BigDecimal.ZERO;

public class ChargeCalculatorImpl implements ChargeCalculator {

    private BigDecimal taxRate;
    private BigDecimal margin;
    private BigDecimal extraMargin;

    public ChargeCalculatorImpl(BigDecimal taxRate, BigDecimal margin, BigDecimal extraMargin) {
        this.taxRate = taxRate;
        this.margin = margin;
        this.extraMargin = extraMargin;
    }

    @Override
    public List<String> calc(Job job) {
        List<PrintItem> printItems = job.getPrintItems();
        // Produce printItem lines to output
        List<String> lines = printItems.stream().map(printItem -> printItem.asString(taxRate)).collect(Collectors.toList());
        // Calculate the "Total" line
        BigDecimal total = printItems.isEmpty() ? ZERO : printItems.stream().map(printItem -> toInvoice(printItem, job.isExtra())).reduce(BigDecimal::add).get();
        // Add total to output
        lines.add("total: $" + total.setScale(2, ROUND_DOWN));
        return lines;
    }

    private BigDecimal toInvoice(PrintItem printItem, boolean extra) {
        return printItem.getCostWithTax(taxRate)
                .add(printItem.getFactoredCost(margin))
                .add(extra ? printItem.getFactoredCost(extraMargin) : ZERO);
    }

}
