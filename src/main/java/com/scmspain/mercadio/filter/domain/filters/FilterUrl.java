package com.scmspain.mercadio.filter.domain.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterUrl implements Filter {
    private final String URL_REGEX = "\\b(http://|https://|HTTP://|HTTPS://)?(www\\.|WWW\\.)?[a-zA-Z0-9_-]*\\.[a-zA-Z]{2,5}\\b";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String finalResult = extractUrls(text);

        logger.info("\nRequest URL filter: \n"+ finalResult);

        return finalResult;
    }

    private String extractUrls(String part) {
        final String lowerPart = part;
        final String[] lineInParts = lowerPart.split(" ");
        final StringBuilder toReturn = new StringBuilder();

        for (int i = 0; i < lineInParts.length; i++) {
            final String lineInPart = lineInParts[i];
            toReturn.append( lineInPart.replaceAll(URL_REGEX,"") + " " );
        }

        return toReturn.toString();
    }
}