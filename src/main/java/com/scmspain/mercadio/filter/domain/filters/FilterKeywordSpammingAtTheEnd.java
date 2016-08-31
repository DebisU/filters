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
        final StringBuilder filteredText = new StringBuilder();
        final List<String> partsSeparatedByPoint = Arrays.asList(text.split("\\."));
        final String filteredLastParagraph = filterLastParagraph(partsSeparatedByPoint.get(partsSeparatedByPoint.size()-1));

        for (int i = 0; i < partsSeparatedByPoint.size()-1 ; i++) {
             filteredText.append(partsSeparatedByPoint.get(i)).append(".");
        }

        filteredText.append(filteredLastParagraph);

        if (textEndWithDot(text)) {
            if (filteredText.toString().charAt(filteredText.toString().length()-1)!='.'){
                filteredText.append(".");
            }
        }

        return filteredText.toString();
    }

    private String filterLastParagraph(String lastParagraph) {
        final StringBuilder filteredText = new StringBuilder();

        if (! lastParagraph.matches(SPAM_END_PATTERN)){
            filteredText.append(lastParagraph);
        }

        return filteredText.toString();
    }

    private boolean textEndWithDot(String text) {
        return text.charAt(text.length()-1) == '.' ? true : false;
    }
}
