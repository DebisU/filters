package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterKeywordMultilineSpamming implements Filter {
    private static final String NUMBERS = ".*[0-9]+.*";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String filteredText = filterKeywordSpamming(text);

        logger.info("\nRequest multiline keyword spamming filter: \n" + filteredText);

        return filteredText;
    }

    private String filterKeywordSpamming(String request) {
        final List<String> paragraphs = CommonStringOperations.splitParagraphs(request);
        final List<String> paragraphsWithMoreThanOneWordList = applyFilterRules(paragraphs);

        return getResultString(paragraphsWithMoreThanOneWordList);
    }

    private String getResultString(List<String> paragraphsWithMoreThanOneWord) {
        final StringBuilder filteredText = new StringBuilder();
        for (String item : paragraphsWithMoreThanOneWord) {
            filteredText.append(item).append("\n");
        }
        return CommonStringOperations.removeLastNewLine(filteredText.toString());
    }

    private List<String> applyFilterRules(List<String> paragraphs) {
        return paragraphs.stream()
                .filter(this::satisfiesFilterRules)
                .collect(Collectors.toList())
                ;
    }

    private boolean satisfiesFilterRules(String paragraph) {
        final List<String> phrase = toPhrase(paragraph);
        if (phrase.isEmpty()) {
            return false;
        }

        if (phrase.size() == 1) {
            return satisfiesRulesForSingleWordPhrase(phrase);
        } else {
            return satisfiesRulesForNonSingleWordPhrase(phrase);
        }
    }

    private List<String> toPhrase(String paragraph) {
        return Arrays.asList(paragraph.trim().split(" "));
    }

    private boolean satisfiesRulesForNonSingleWordPhrase(List<String> phrase) {
        return phrase.size() > 1;
    }

    private boolean satisfiesRulesForSingleWordPhrase(List<String> phrase) {
        if (phrase.size() != 1) {
            return false;
        }
        final String word = phrase.get(0);

        return containsPrefixes(word)
                || containsSuffixes(word)
                || isSeparator(word)
                || isNumber(word);
    }

    private boolean isNumber(String word) {
        return word.matches(NUMBERS);
    }

    private boolean isSeparator(String word) {
        final String regex = "^\\n$";
        return word.matches(regex);
    }

    private boolean containsPrefixes(String word) {
        final String regex = "^(-|>|·|#|º|¬).*";
        return word.matches(regex);
    }

    private boolean containsSuffixes(String word) {
        final String regex = ".*(,|\\.|-|:|;|\\.\\.\\.)";
        return word.matches(regex);
    }
}
