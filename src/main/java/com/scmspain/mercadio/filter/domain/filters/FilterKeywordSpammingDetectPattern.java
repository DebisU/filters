package com.scmspain.mercadio.filter.domain.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterKeywordSpammingDetectPattern implements Filter{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String WORDS_SEPARATED_BY_COMMAS = "([a-zA-Z0-9 ]{6,14},\\s)([a-zA-Z0-9 ]{6,14}(\\.|\n)+)?";
    @Override
    public String filter(String text) {
        final String finalResult = checkIfKeywordSpamming(text);

        logger.info("\nRequest Keyword spamming filter by common words: \n"+ finalResult);

        return finalResult;
    }

    private String checkIfKeywordSpamming(String text) {
        final String toReturn = text.replaceAll(WORDS_SEPARATED_BY_COMMAS,"");

        return toReturn;
    }
}
