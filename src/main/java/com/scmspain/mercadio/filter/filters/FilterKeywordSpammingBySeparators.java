package com.scmspain.mercadio.filter.filters;

import com.scmspain.mercadio.filter.utils.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FilterKeywordSpammingBySeparators implements Filter {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final int MIN_SEPARATOR_DIVIDER = 12;
    private static final String NUMBER_AND_WHATEVER_REGEX = "^[0-9].*";
    private static final String INCHES_REGEX = ".*?[0-9],[0-9]\".*?";
    private static final String DIMENSIONS_REGEX = ".*?[0-9]{1,6},[0-9]{1,6}.*?";

    private final Optional<String> extraArg;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FilterKeywordSpammingBySeparators(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }

    @Override
    public String filter(String text) {
        final String filteredText = checkIfKeywordSpamming(text, extraArg);

        logger.info("\nRequest Keyword spamming filter: \n" + filteredText);

        return filteredText;
    }

    private String checkIfKeywordSpamming(String request, Optional<String> extraArg) {
        final List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);

        return getParagraphsWithoutRepeatedSeparator(separatedParagraphs,extraArg);
    }

    private String getParagraphsWithoutRepeatedSeparator(List<String> separatedParagraphs, Optional<String> extraArg) {
        final StringBuilder filteredText = new StringBuilder();
        final char separator = extraArg != null && extraArg.isPresent() && !extraArg.get().isEmpty()
                ? extraArg.get().charAt(0) : DEFAULT_SEPARATOR;

        separatedParagraphs.stream().filter(separatedParagraph ->
                knowIfExceedsAmountOfSeparators(
                        separatedParagraph, separator)
                        || separatedParagraph.split(" ").length == 1)
                .forEach(separatedParagraph -> filteredText.append(separatedParagraph).append("\n"));

        return CommonStringOperations.removeLastNewLine(filteredText.toString());
    }

    private boolean knowIfExceedsAmountOfSeparators(String paragraph, char separator) {
        int amountOfSeparatorsInParagraph = 0;
        for (int i = 0 ; i < paragraph.length() ; i++) {
            if (paragraph.charAt(i) == separator) {
                amountOfSeparatorsInParagraph++;
                if (checkConditions(paragraph)) {
                    return true;
                }
            }
        }
        final int maxSeparators = paragraph.length() / MIN_SEPARATOR_DIVIDER;

        return amountOfSeparatorsInParagraph <= maxSeparators;
    }

    private boolean checkConditions(String paragraph) {
        return paragraph.trim().matches(NUMBER_AND_WHATEVER_REGEX)
                || paragraph.trim().matches(DIMENSIONS_REGEX)
                || paragraph.trim().matches(INCHES_REGEX);
    }
}
