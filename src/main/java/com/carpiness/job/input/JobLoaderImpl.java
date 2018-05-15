package com.carpiness.job.input;

import com.carpiness.job.config.AppState;
import com.carpiness.job.domain.Item;
import com.carpiness.job.domain.Job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

public class JobLoaderImpl implements JobLoader {

    private static final String KEYWORD_EXTRA_MARGIN = "extra-margin";
    private static final String KEYWORD_EXEMPT       = "exempt";

    @Override
    public Job load(Iterable<String> strings) {
        Iterator<String> stringIterator = strings.iterator();
        Optional<String> preview = stringIterator.hasNext() ? Optional.of(stringIterator.next()) : Optional.empty();
        List<Item> items = new ArrayList<>();
        boolean extra = false;
        if (preview.isPresent() && AppState.INSTANCE.compare(KEYWORD_EXTRA_MARGIN, preview.get()) ) {
            extra = true;
            preview = stringIterator.hasNext() ? Optional.of(stringIterator.next()) : Optional.empty();
        }
        Job job = new Job(extra, items);
        while (preview.isPresent()) {
            Optional<Item> oItem = parseItemLine(preview.get());
            oItem.ifPresent(items::add);
            preview = stringIterator.hasNext() ? Optional.of(stringIterator.next()) : Optional.empty();
        }
        return job;
    }

    private Optional<Item> parseItemLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        Optional<String> name = tokenizer.hasMoreTokens() ? Optional.of(tokenizer.nextToken()) : Optional.empty();
        Optional<String> value = tokenizer.hasMoreTokens() ? Optional.of(tokenizer.nextToken()) : Optional.empty();
        Optional<String> exempt = tokenizer.hasMoreTokens() ? Optional.of(tokenizer.nextToken()) : Optional.empty();
        if (name.isPresent() && value.isPresent()) {
            try {
                Double dblValue = Double.valueOf(value.get());
                return Optional.of(new Item(name.get(), dblValue, exempt.isPresent() && AppState.INSTANCE.compare(KEYWORD_EXEMPT, exempt.get())));
            } catch (NumberFormatException ex) {
                checkFailFast(line);
            }
        } else {
            checkFailFast(line);
        }
        return Optional.empty();
    }

    private void checkFailFast(String line) {
        if (AppState.INSTANCE.isFailFast()) {
            throw new IllegalStateException("Unable parse Item string: " + line);
        }
    }

}
