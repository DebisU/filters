package domain.filters;

import domain.factories.CommonStringOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterMultilineKeywordSpamming implements Filter {
    private static final String NUMBERS = ".*[0-9]+.*";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filter(String text) {
        final String finalResult = checkIfKeywordSpamming(text);

        logger.info("\nRequest multiline keyword spamming filter: \n"+ finalResult);

        return finalResult;
    }

    private String checkIfKeywordSpamming(String request) {
        final List<String> paragraphs = CommonStringOperations.splitParagraphs(request);
        final List<String> paragraphsWithMoreThanOneWordList = getSeparatedParagraphs(paragraphs);
        final String paragraphsWithMoreThanOneWordString = getResultString(paragraphsWithMoreThanOneWordList);

        return paragraphsWithMoreThanOneWordString;
    }

    private String getResultString(List<String> paragraphsWithMoreThanOneWord) {
        String toReturn = "";
        for (String item : paragraphsWithMoreThanOneWord) {
            toReturn += item + "\n";
        }
        return CommonStringOperations.removeLastNewLine(toReturn);
    }

    private List<String> getSeparatedParagraphs(List<String> paragraphs) {
        final List<String> paragraphsWithMoreThanOneWord = new ArrayList<>();

        for (String item : paragraphs) {
            final List<String> splittedAndTrimedItem = Arrays.asList(item.trim().split(" "));
            if ( (splittedAndTrimedItem.size() > 1)
                    || (containsIndexer(splittedAndTrimedItem.get(0)) && splittedAndTrimedItem.size() == 1)
                    || (containsSuffixes(splittedAndTrimedItem.get(0)) && splittedAndTrimedItem.size() == 1)
                    || (splittedAndTrimedItem.get(0) == "\n" && splittedAndTrimedItem.size() == 1)
                    || (splittedAndTrimedItem.get(0).matches(NUMBERS) && splittedAndTrimedItem.size() == 1) ){
                paragraphsWithMoreThanOneWord.add(item);
            }
        }
        return paragraphsWithMoreThanOneWord;
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
