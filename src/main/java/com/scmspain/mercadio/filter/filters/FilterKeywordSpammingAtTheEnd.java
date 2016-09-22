package com.scmspain.mercadio.filter.filters;

import java.util.Arrays;
import java.util.List;

public class FilterKeywordSpammingAtTheEnd implements Filter {

    private static final String SPAM_END_PATTERN = ".*(\\w+[,._-]\\s?){8,}.*";
    public static final int MIN_TEXT_LENGTH_TO_ANALYZE = 150;

    @Override
    public String filter(String text) {
        return deleteSpamAtTheEnd(text);
    }


    private String deleteSpamAtTheEnd(String text) {
        if (getIfItsShortText(text)) {
            return filterText(text);
        }
        return text;
    }

    private String filterText(String text) {
        final StringBuilder filteredText = new StringBuilder();
        final List<String> partsSeparatedByPoint = Arrays.asList(text.split("\\."));
        final String filteredLastParagraph =
                filterLastParagraph(partsSeparatedByPoint.get(partsSeparatedByPoint.size() - 1));

        for (int i = 0; i < partsSeparatedByPoint.size() - 1 ; i++) {
            filteredText.append(partsSeparatedByPoint.get(i)).append(".");
        }

        filteredText.append(filteredLastParagraph);

        if (text.length() > 0
                && filteredText.toString().charAt(filteredText.toString().length() - 1) != '.'
                && textEndWithDot(text)) {
            filteredText.append(".");
        }

        return filteredText.toString();
    }

    private String filterLastParagraph(String lastParagraph) {
        final StringBuilder filteredText = new StringBuilder();

        if (! lastParagraph.matches(SPAM_END_PATTERN)) {
            filteredText.append(lastParagraph);
        }

        return filteredText.toString();
    }

    private boolean getIfItsShortText(String text) {
        if (text.length() > MIN_TEXT_LENGTH_TO_ANALYZE) {
            return true;
        }
        return false;
    }

    private boolean textEndWithDot(String text) {
        return text.charAt(text.length() - 1) == '.';
    }
}
