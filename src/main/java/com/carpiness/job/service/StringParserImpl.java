package com.carpiness.job.service;

import com.carpiness.job.App;
import com.carpiness.job.config.ApplicationContext;
import com.carpiness.job.domain.PrintItem;
import com.carpiness.job.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class StringParserImpl implements StringParser {

    private static final String KEYWORD_EXTRA_MARGIN = "extra-margin";
    private static final String KEYWORD_EXEMPT       = "exempt";

    private static final Logger LOG = LoggerFactory.getLogger(StringParserImpl.class);

    private ApplicationContext context = ApplicationContext.INSTANCE;

    @Override
    public Job load(Iterable<String> strings) {
        Iterator<String> stringIterator = strings.iterator();

        Optional<String> peekedString = nextString(stringIterator);
        List<PrintItem> printItems = new ArrayList<>();

        boolean extra = false;
        if (peekedString.isPresent() && context.compare(KEYWORD_EXTRA_MARGIN, peekedString.get()) ) {
            extra = true;
            peekedString = nextString(stringIterator);
        }

        Job job = new Job(extra, printItems);

        while (peekedString.isPresent()) {
            parseItemLine(peekedString.get()).ifPresent(printItems::add);

            peekedString = nextString(stringIterator);
        }

        return job;
    }

    private Optional<PrintItem> parseItemLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        Optional<String> name = nextToken(tokenizer);
        Optional<String> value = nextToken(tokenizer);
        Optional<String> exempt = nextToken(tokenizer);

        if (name.isPresent() && value.isPresent()) {
            try {
                Double dblValue = Double.valueOf(value.get());
                return of(new PrintItem(name.get(), dblValue, exempt.isPresent() && context.compare(KEYWORD_EXEMPT, exempt.get())));
            } catch (NumberFormatException ex) {
                LOG.error("Unable convert string to number: {}", value.get());
            }
        }

        context.checkFailFast("Unable parse: " + line);

        return empty();
    }

    private Optional<String> nextString(Iterator<String> stringIterator) {
        return stringIterator.hasNext() ? of(stringIterator.next()) : empty();
    }

    private Optional<String> nextToken(StringTokenizer tokenizer) {
        return tokenizer.hasMoreTokens() ? of(tokenizer.nextToken()) : empty();
    }

}
