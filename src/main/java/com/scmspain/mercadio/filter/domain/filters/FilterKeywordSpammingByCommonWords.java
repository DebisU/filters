package com.scmspain.mercadio.filter.domain.filters;

import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterKeywordSpammingByCommonWords implements Filter {
    public static final int MIN_LINE_LENGTH_TO_ANALYZE = 55;
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
        final List<String> mostCommonWords = getMostCommonWords();
        final List<String> wordsToMatch = getWordsToMatch();


        for (int i = 0 ; i < separatedParagraphs.size() ; i++) {
            if (CommonStringOperations.checkIfStringContainsItemFromList(separatedParagraphs.get(i),mostCommonWords)
                    || CommonStringOperations.checkIfStringMatchesItemFromList(separatedParagraphs.get(i),wordsToMatch)
                    || separatedParagraphs.get(i).length()< MIN_LINE_LENGTH_TO_ANALYZE) {
                paragraphsWithPrepositions.add(separatedParagraphs.get(i));
            }
        }

        final String paragraphsWithPrepositionsStr = getResultAsString(paragraphsWithPrepositions);

        return paragraphsWithPrepositionsStr;
    }

    private String getResultAsString(List<String> paragraphsWithPrepositions) {
        String paragraphsWithPrepositionsStr = "";
        for (int i = 0 ; i < paragraphsWithPrepositions.size() ; i++) {
            paragraphsWithPrepositionsStr += paragraphsWithPrepositions.get(i) + "\n";

        }
        return CommonStringOperations.removeLastNewLine(paragraphsWithPrepositionsStr);
    }

    private static List<String> getMostCommonWords() {
        final List<String> commonWords = new ArrayList<>(getAllPrepositions());
        commonWords.addAll(getMostCommonSpanishWords());

        return commonWords;
    }

    private static List<String> getWordsToMatch() {
        final List<String> wordsToMatch = new ArrayList<>(getUnitsOfMeasure());
        wordsToMatch.addAll(getSpecificationWords());

        return wordsToMatch;
    }

    private static List<String> getAllPrepositions() {
        List<String> prepositions = Arrays.asList(" a "," ante "," bajo "," cabe "," con "," contra "," de "," desde "," en "," entre "," hacia "," hasta "," para "," por "," según "," segun "," sin "," so "," sobre "," tras ");
        return prepositions;
    }

    private static List<String> getMostCommonSpanishWords() {
        List<String> commonWords = Arrays.asList(" la "," que "," el "," los "," se "," del "," un "," su "," se "," es "," no "," si ");
        return commonWords;
    }

    private static List<String> getSpecificationWords() {
        List<String> techWords = Arrays.asList("3D", "HD");
        return techWords;
    }

    private static List<String> getUnitsOfMeasure() {
        List<String> unitsOfMeasure = Arrays.asList("mhz","ghz","hz","m²","m³","km","gb","mb","tb","mpx","cm","mm");
        return unitsOfMeasure;
    }
}
