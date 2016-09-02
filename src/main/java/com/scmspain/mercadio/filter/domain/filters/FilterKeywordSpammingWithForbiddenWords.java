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
        final String filteredText = checkIfKeywordSpamming(text,extraArg);

        logger.info("\nRequest Keyword spamming with forbidden words: \n"+ filteredText);

        return filteredText;
    }

    private String checkIfKeywordSpamming(String request, Optional<String> extraArg) {
        final List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);
        final StringBuilder filteredText = new StringBuilder();

        final List<String> forbiddenWords = (extraArg != null && extraArg.isPresent()) ?  getForbiddenWords(extraArg) : new ArrayList<>();

        separatedParagraphs.stream().filter(separatedParagraph ->
                !CommonStringOperations.checkIfStringContainsItemFromList(separatedParagraph.toLowerCase(), forbiddenWords))
                .forEach(separatedParagraph -> filteredText.append(separatedParagraph).append("\n"));

        return CommonStringOperations.removeLastNewLine(filteredText.toString());
    }


    private List<String> getForbiddenWords(Optional<String> request) {
        final String requestStr = request.isPresent() && request.get() != null ? request.get() : "";
        return Arrays.asList(requestStr.split(DEFAULT_SEPARATOR));
    }
}
