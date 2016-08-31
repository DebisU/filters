package com.scmspain.mercadio.filter.domain.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FilterUrl implements Filter {
    private final String URL_REGEX = "\\b(http://|https://|HTTP://|HTTPS://|http://www\\.|https://www\\.|HTTPS://WWW\\.|HTTP://www\\.)?(www\\.|WWW\\.|http://|https://|HTTPS://|HTTP://)[a-zA-Z0-9_-]*\\.[a-zA-Z]{2,5}\\b";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Optional<String> extraArg;

    public FilterUrl(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }


    @Override
    public String filter(String text) {
        final String filteredText = takeDecision(text);

        logger.info("\nRequest URL filter: \n "+ filteredText.toString());

        return filteredText.toString();
    }

    private String takeDecision(String text) {
        final StringBuilder filteredText = new StringBuilder();

        if (extraArg.get().equals("delete")) {
            filteredText.append(extractUrls(text));
        } else if (extraArg.get().equals("replace")) {
            filteredText.append(replacePrefix(text));
        } else {
            filteredText.append(extractUrls(text));
        }

        return filteredText.toString();
    }

    private String replacePrefix(String text) {
        final StringBuilder filteredText = new StringBuilder();
        final String[] lineInParts = text.split(" ");

        for (int i = 0; i < lineInParts.length; i++) {
            final String lineInPart = replaceURLPrefixes(lineInParts[i]);

            filteredText.append( lineInPart.replaceAll(URL_REGEX,"") + " " );
        }

        return filteredText.toString();
    }

    private String extractUrls(String part) {
        final String lowerPart = part;
        final String[] lineInParts = lowerPart.split(" ");
        final StringBuilder filteredText = new StringBuilder();

        for (int i = 0; i < lineInParts.length; i++) {
            final String lineInPart = lineInParts[i];
            filteredText.append( lineInPart.replaceAll(URL_REGEX,"") + " " );
        }

        return filteredText.toString();
    }

    private String replaceURLPrefixes(String text) {
        return text
                .replace("www.","")
                .replace("WWW.","")
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