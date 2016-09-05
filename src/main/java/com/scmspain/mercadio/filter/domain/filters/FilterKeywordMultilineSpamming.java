package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FilterKeywordMultilineSpamming implements Filter {
    private static final String NUMBERS = ".*[0-9]+.*";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String filteredText = checkIfKeywordSpamming(text);

        logger.info("\nRequest multiline keyword spamming filter: \n" + filteredText);

        return filteredText;
    }

    private String checkIfKeywordSpamming(String request) {
        final List<String> paragraphs = CommonStringOperations.splitParagraphs(request);
        final List<String> paragraphsWithMoreThanOneWordList = getSeparatedParagraphs(paragraphs);

        return getResultString(paragraphsWithMoreThanOneWordList);
    }

    private String getResultString(List<String> paragraphsWithMoreThanOneWord) {
        final StringBuilder filteredText = new StringBuilder();
        for (String item : paragraphsWithMoreThanOneWord) {
            filteredText.append(item).append("\n");
        }
        return CommonStringOperations.removeLastNewLine(filteredText.toString());
    }

    private List<String> getSeparatedParagraphs(List<String> paragraphs) {
        final List<String> paragraphsWithMoreThanOneWord = new ArrayList<>();

        for (String item : paragraphs) {
            final List<String> splittedAndTrimedItem = Arrays.asList(item.trim().split(" "));
            if (checkConditions(splittedAndTrimedItem)) {
                paragraphsWithMoreThanOneWord.add(item);
            }
        }
        return paragraphsWithMoreThanOneWord;
    }

    private boolean checkConditions(List<String> splittedAndTrimedItem) {
        return ( (splittedAndTrimedItem.size() > 1)
                || (containsIndexer(splittedAndTrimedItem.get(0)) && splittedAndTrimedItem.size() == 1)
                || (containsSuffixes(splittedAndTrimedItem.get(0)) && splittedAndTrimedItem.size() == 1)
                || (Objects.equals(splittedAndTrimedItem.get(0), "\n") && splittedAndTrimedItem.size() == 1)
                || (splittedAndTrimedItem.get(0).matches(NUMBERS) && splittedAndTrimedItem.size() == 1) );
    }


    private boolean containsIndexer(String word) {
        final String regex = "^(-|>|·|#|º|¬).*";
        return word.matches(regex);
    }

    private boolean containsSuffixes(String word) {
        final String regex = ".*(,|\\.|-|:|;|\\.\\.\\.)";
        return word.matches(regex);
    }
}
