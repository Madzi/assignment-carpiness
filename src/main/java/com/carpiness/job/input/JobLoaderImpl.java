package com.carpiness.job.input;

import com.carpiness.job.config.AppState;
import com.carpiness.job.domain.Job;
import com.carpiness.job.input.token.ExemptToken;
import com.carpiness.job.input.token.ExtraMarginToken;
import com.carpiness.job.input.token.IdentifierToken;
import com.carpiness.job.input.token.NumberToken;
import com.carpiness.job.input.token.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;

public class JobLoaderImpl implements JobLoader {

    private static final String KEYWORD_EXTRA_MARGIN = "extra-margin";
    private static final String KEYWORD_EXEMPT       = "exempt";

    @Override
    public Job load(Iterable<String> strings) {
        boolean extra = false;
        List<Token> tokens = new ArrayList<>();
        strings.forEach(line -> tokens.addAll(parseLine(line)));
        Iterator<Token> iterator = tokens.iterator();
        Token token = iterator.hasNext() ? iterator.next() : null;
        if (token == null) {
            return new Job();
        }
        return null;
    }

    private List<Token> parseLine(String line) {
        List<Token> tokens = new ArrayList<>();
        if (AppState.INSTANCE.compare(KEYWORD_EXTRA_MARGIN, line)) {
            tokens.add(new ExtraMarginToken());
        } else {
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                String element = tokenizer.nextToken();
                if (AppState.INSTANCE.compare(KEYWORD_EXEMPT, element)) {
                    tokens.add(new ExemptToken());
                } else if (isDigit(element.charAt(0))) {
                    tokens.add(new NumberToken(Double.parseDouble(element)));
                } else {
                    tokens.add(new IdentifierToken(element));
                }
            }
        }
        return tokens;
    }

}
