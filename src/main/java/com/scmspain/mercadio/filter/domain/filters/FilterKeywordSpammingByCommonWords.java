package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterKeywordSpammingByCommonWords implements Filter {
    private static final int MIN_LINE_LENGTH_TO_ANALYZE_REGEX = 55;
    private static final String BANNER_TEXT_REGEX = "[<>·#._,-]{8,}[a-zA-Z0-9 ]+[<>·#._,-]{8,}";
    private static final String LINE_STARTS_WITH_NUMBERS_REGEX = "[0-9]+.*?";
    private static final String SEPARATOR_LINE_REGEX = "[*+_-]{15,}";
    private static final String CONTAINS_A_LOT_OF_EXCLAMATION_AND_INTERROGATION_REGEX = ".*?[!¡¿?]{5,}.*?";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String filteredText = checkIfKeywordSpamming(text);

        logger.info("\nRequest Keyword spamming filter by common words: \n"+ filteredText);

        return filteredText;
    }

    private String checkIfKeywordSpamming(String request) {
        List<String> separatedParagraphs = CommonStringOperations.splitParagraphs(request);
        return  getParagraphsWithCommonWords(separatedParagraphs);
    }

    private String getParagraphsWithCommonWords(List<String> separatedParagraphs) {
        final List<String> paragraphsWithPrepositions = separatedParagraphs.stream().filter(this::checkConditions).collect(Collectors.toList());

        return getResultAsString(paragraphsWithPrepositions);
    }

    private boolean checkConditions(String text) {
        return CommonStringOperations.checkIfStringContainsItemFromList(text, getMostCommonWords())
                || CommonStringOperations.checkIfStringMatchesItemFromList(text, getWordsToMatch())
                || getAmountOfSpecificCharacters(text) >= 8
                || text.trim().matches(BANNER_TEXT_REGEX)
                || text.trim().matches(CONTAINS_A_LOT_OF_EXCLAMATION_AND_INTERROGATION_REGEX)
                || text.trim().matches(SEPARATOR_LINE_REGEX)
                || text.trim().matches(LINE_STARTS_WITH_NUMBERS_REGEX)
                || text.length() < MIN_LINE_LENGTH_TO_ANALYZE_REGEX;
    }

    private String getResultAsString(List<String> paragraphsWithPrepositions) {
        final StringBuilder paragraphsWithPrepositionsStr = new StringBuilder();
        for (String paragraphsWithPreposition : paragraphsWithPrepositions) {
            paragraphsWithPrepositionsStr.append(paragraphsWithPreposition).append("\n");
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
        return isGreater(amountOfPoints, amountOfComas)
                ? amountOfComas : (isGreater(amountOfLowBar, amountOfPoints))
                ? amountOfPoints : amountOfLowBar;
    }

    private static boolean isGreater(int amountOfPoints, int amountOfComas) {
        return amountOfComas > amountOfPoints;
    }

}
