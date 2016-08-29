package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FilterKeywordSpammingBySeparators implements Filter {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final int MIN_SEPARATOR_DIVIDER = 12;
    private final Optional<String> extraArg;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FilterKeywordSpammingBySeparators(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }

    @Override
    public String filter(String text) {
        final String finalResult = checkIfKeywordSpamming(text, extraArg);

        logger.info("\nRequest Keyword spamming filter: \n"+ finalResult);

        return finalResult;
    }

    private String checkIfKeywordSpamming(String request, Optional<String> extraArg) {
        final List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);

        return getParagraphsWithoutRepeatedSeparator(separatedParagraphs,extraArg);
    }

    private String getParagraphsWithoutRepeatedSeparator(List<String> separatedParagraphs, Optional<String> extraArg) {
        final StringBuilder toReturn = new StringBuilder();
        final char separator = (extraArg != null && extraArg.isPresent() && !extraArg.get().isEmpty()) ? extraArg.get().charAt(0) : DEFAULT_SEPARATOR;

        for (int i = 0; i < separatedParagraphs.size(); i++) {
            if (knowIfExceedsAmountOfSeparators(separatedParagraphs.get(i),separator)
                    || separatedParagraphs.get(i).split(" ").length == 1) {
                toReturn.append(separatedParagraphs.get(i) + "\n");
            }
        }

        return CommonStringOperations.removeLastNewLine(toReturn.toString());
    }

    private boolean knowIfExceedsAmountOfSeparators(String paragraph, char separator) {
        int amountOfSeparatorsInParagraph = 0;
        for (int i = 0 ; i < paragraph.length() ; i++) {
            if (paragraph.charAt(i) == separator) {
                amountOfSeparatorsInParagraph++;
                if (paragraph.trim().matches("^[0-9].*")) {
                    return true;
                }
            }
        }

        final int maxSeparators = paragraph.length()/ MIN_SEPARATOR_DIVIDER;

        return amountOfSeparatorsInParagraph > maxSeparators ? false : true;
    }
}
