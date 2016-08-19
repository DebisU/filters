package com.scmspain.mercadio.filter.domain.filters;

public class FilterKeywordSpammingAtTheEnd implements Filter {
    private final String SPAM_END = "((((\\s)|\\.|:|;|\\*|!)(\\s)|\\.|:|;|\\*|!)|\n|(.*)?[0-9])";

    @Override
    public String filter(String text) {
        String toReturn = deleteSpamAtTheEnd(text);

        return toReturn;
    }

    private String deleteSpamAtTheEnd(String text) {
        String toReturn = "";

        boolean foundedEndOfSpam = false;
        int index = text.length()-1;
        do {
            index--;
            if (!foundedEndOfSpam && index >= 1) {
                final String toEvaluate = new StringBuilder(String.valueOf(text.charAt(index)).toString() + String.valueOf(text.charAt(index-1)).toString()).reverse().toString();
                if (toEvaluate.matches(SPAM_END)) {
                    foundedEndOfSpam = true;
                    toReturn += text.charAt(index);
                }
            } else {
                    toReturn += text.charAt(index);
            }
        } while (index > 0);

        toReturn = new StringBuilder(toReturn).reverse().toString();
        return toReturn != "" ? toReturn : text;
    }
}
