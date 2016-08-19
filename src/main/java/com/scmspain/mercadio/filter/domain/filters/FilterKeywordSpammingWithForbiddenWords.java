package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FilterKeywordSpammingWithForbiddenWords implements Filter {
    private static final String DEFAULT_SEPARATOR = ",";
    private final Optional<String> extraArg;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FilterKeywordSpammingWithForbiddenWords(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }

    @Override
    public String filter(String text) {
        final String finalResult = checkIfKeywordSpamming(text,extraArg);

        logger.info("\nRequest Keyword spamming with forbidden words: \n"+ finalResult);

        return finalResult;
    }

    private String checkIfKeywordSpamming(String request, Optional<String> extraArg) {
        final List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);
        String toReturn= "";

        final List<String> forbiddenWords = (extraArg != null && extraArg.isPresent()) ?  getForbiddenWords(extraArg) : new ArrayList<>();

        for (int i = 0 ; i < separatedParagraphs.size() ; i++) {
            if (! CommonStringOperations.checkIfStringContainsItemFromList(separatedParagraphs.get(i),forbiddenWords)) {
                toReturn += separatedParagraphs.get(i) + "\n";
            }
        }

        return CommonStringOperations.removeLastNewLine(toReturn);
    }


    private List<String> getForbiddenWords(Optional<String> request) {
        final String requestStr = request.get();
        return Arrays.asList(requestStr.split(DEFAULT_SEPARATOR));
    }
}
