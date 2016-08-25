package com.scmspain.mercadio.filter.domain.filters;

import java.util.Arrays;
import java.util.List;

public class FilterKeywordSpammingAtTheEnd implements Filter {
    private final String SPAM_END_PATTERN = ".*(\\w+[,._-]\\s?){8,}.*";

    @Override
    public String filter(String text) {
        final String filteredText = deleteSpamAtTheEnd(text);

        return filteredText;
    }


    private String deleteSpamAtTheEnd(String text) {
        final StringBuilder toReturn = new StringBuilder();
        final List<String> partsSeparatedByPoint = Arrays.asList(text.split("\\."));
        final String filteredLastParagraph = filterLastParagraph(partsSeparatedByPoint.get(partsSeparatedByPoint.size()-1));

        for (int i = 0; i < partsSeparatedByPoint.size()-1 ; i++) {
             toReturn.append(partsSeparatedByPoint.get(i)).append(".");
        }

        toReturn.append(filteredLastParagraph);

        if (textEndWithDot(text)) {
            if (toReturn.toString().charAt(toReturn.toString().length()-1)!='.'){
                toReturn.append(".");
            }
        }

        return toReturn.toString();
    }

    private String filterLastParagraph(String lastParagraph) {
        final StringBuilder toReturn = new StringBuilder();

        if (! lastParagraph.matches(SPAM_END_PATTERN)){
            toReturn.append(lastParagraph);
        }

        return toReturn.toString();
    }

    private boolean textEndWithDot(String text) {
        return text.charAt(text.length()-1) == '.' ? true : false;
    }
}
