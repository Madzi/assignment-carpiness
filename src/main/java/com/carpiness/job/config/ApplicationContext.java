package com.carpiness.job.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Singleton for keeping application context with configuration.
 */
public enum ApplicationContext {

    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContext.class);

    private boolean ignoreCase = false;
    private boolean failFast   = true;

    private BigDecimal taxRate     = new BigDecimal(0.07);
    private BigDecimal margin      = new BigDecimal(0.11);
    private BigDecimal extraMargin = new BigDecimal(0.05);

    public void switchOffFailFast() {
        failFast = false;
    }

    public void switchOffCaseSensitivity() {
        ignoreCase = true;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public boolean isFailFast() {
        return failFast;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public BigDecimal getExtraMargin() {
        return extraMargin;
    }

    public boolean compare(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return ignoreCase ? str1.equalsIgnoreCase(str2) : str1.equals(str2);
        }
    }

    public void checkFailFast(String message) {
        if (failFast) {
            LOG.error("Illegal state: {} - Fail Fast", message);
            throw new IllegalStateException(message);
        }
        LOG.warn("Illegal state: {} - Skip");
    }

}
