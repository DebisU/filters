package com.scmspain.mercadio.filter.domain.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FilterUrl implements Filter {
    private static final String URL_REGEX = "\\b(http://|https://|HTTP://|HTTPS://|http://www\\.|https://www\\.|HTTPS://WWW\\.|HTTP://www\\.)?(www\\.|WWW\\.|http://|https://|HTTPS://|HTTP://)[a-zA-Z0-9_-]*\\.[a-zA-Z]{2,5}\\b";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Optional<String> extraArg;

    public FilterUrl(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }


    @Override
    public String filter(String text) {
        final String filteredText = takeDecision(text);

        logger.info("\nRequest URL filter: \n " + filteredText);

        return filteredText;
    }

    private String takeDecision(String text) {
        final StringBuilder filteredText = new StringBuilder();

        if (extraArg.isPresent() && "delete".equalsIgnoreCase(extraArg.get())) {
            filteredText.append(extractUrls(text));
        } else if (extraArg.isPresent() && "replace".equalsIgnoreCase(extraArg.get())) {
            filteredText.append(replacePrefix(text));
        } else {
            filteredText.append(extractUrls(text));
        }

        return filteredText.toString();
    }

    private String replacePrefix(String text) {
        final StringBuilder filteredText = new StringBuilder();
        final String[] lineInParts = text.split(" ");

        for (String lineInPart1 : lineInParts) {
            final String lineInPart = replaceUrlPrefixes(lineInPart1);

            filteredText.append(lineInPart.replaceAll(URL_REGEX, "")).append(" ");
        }

        return filteredText.toString();
    }

    private String extractUrls(String part) {
        final String[] lineInParts = part.split(" ");
        final StringBuilder filteredText = new StringBuilder();

        for (final String lineInPart : lineInParts) {
            filteredText.append(lineInPart.replaceAll(URL_REGEX, "")).append(" ");
        }

        return filteredText.toString();
    }

    private static String replaceUrlPrefixes(String text) {
        return text
                .replace("www.","")
                .replace("WWW.","")
                .replace("http://","")
                .replace("https://","")
                .replace("HTTP://","")
                .replace("HTTPS://","")
                .replace("http://www.","")
                .replace("https://www.","")
                .replace("https://WWW.","")
                .replace("https://WWW.","")
                .replace("HTTP://WWW.","")
                .replace("HTTPS://WWW.","")
                .replace("HTTPS://www.","")
                .replace("HTTP://www.","");
    }
}