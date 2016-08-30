package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FilterKeywordSpammingByCommonWords implements Filter {
    public static final int MIN_LINE_LENGTH_TO_ANALYZE = 55;
    private static final String BANNER_TEXT_REGEX = "[<>·#._,-]{8,}[a-zA-Z0-9 ]+[<>·#._,-]{8,}";
    private static final String LINE_STARTS_WITH_NUMBERS = "[0-9]+.*?";
    public static final String SEPARATOR_LINE_REGEX = "[*+_-]{15,}";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String finalResult = checkIfKeywordSpamming(text);

        logger.info("\nRequest Keyword spamming filter by common words: \n"+ finalResult);

        return finalResult;
    }

    private String checkIfKeywordSpamming(String request) {
        List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);
        return  getParagraphsWithCommonWords(separatedParagraphs);
    }

    private String getParagraphsWithCommonWords(List<String> separatedParagraphs) {
        final List<String> paragraphsWithPrepositions = new ArrayList<>();

        for (int i = 0 ; i < separatedParagraphs.size() ; i++) {
            if (checkConditions(separatedParagraphs.get(i))) {
                paragraphsWithPrepositions.add(separatedParagraphs.get(i));
            }
        }

        final String paragraphsWithPrepositionsStr = getResultAsString(paragraphsWithPrepositions);

        return paragraphsWithPrepositionsStr;
    }

    private boolean checkConditions(String text) {
        if (CommonStringOperations.checkIfStringContainsItemFromList(text,getMostCommonWords())
                || CommonStringOperations.checkIfStringMatchesItemFromList(text,getWordsToMatch())
                || getAmountOfSpecificCharacters(text) >= 8
                || text.trim().matches(BANNER_TEXT_REGEX)
                || text.trim().matches(SEPARATOR_LINE_REGEX)
                || text.trim().matches(LINE_STARTS_WITH_NUMBERS)
                || text.length()< MIN_LINE_LENGTH_TO_ANALYZE) {
            return true;
        }
        return false;
    }

    private String getResultAsString(List<String> paragraphsWithPrepositions) {
        final StringBuilder paragraphsWithPrepositionsStr = new StringBuilder();
        for (int i = 0 ; i < paragraphsWithPrepositions.size() ; i++) {
            paragraphsWithPrepositionsStr.append(paragraphsWithPrepositions.get(i) + "\n");

        }
        return CommonStringOperations.removeLastNewLine(paragraphsWithPrepositionsStr.toString());
    }

    private static List<String> getMostCommonWords() {
        final List<String> commonWords = new ArrayList<>(CommonStringOperations.getAllPrepositions());
        commonWords.addAll(CommonStringOperations.getMostCommonSpanishWords());
        commonWords.addAll(CommonStringOperations.getTimeUnits());

        return commonWords;
    }

    private static List<String> getWordsToMatch() {
        final List<String> wordsToMatch = new ArrayList<>(CommonStringOperations.getAbbreviatedUnitsOfMeasure());
        wordsToMatch.addAll(CommonStringOperations.getSpecificationWords());

        return wordsToMatch;
    }

    private static int getAmountOfSpecificCharacters(String text) {
        int amountOfPoints = 0;
        int amountOfComas = 0;
        int amountOfLowBar = 0;

        for (int i = 0 ; i < text.length() ; i++) {
            switch (text.charAt(i)) {
                case '.': amountOfPoints++;
                    break;
                case ',': amountOfComas++;
                    break;
                case '_': amountOfLowBar++;
                    break;
                default:
                    break;
            }
        }
        return amountOfComas > amountOfPoints ? amountOfComas : amountOfPoints > amountOfLowBar ? amountOfPoints : amountOfLowBar;
    }

}
