package com.scmspain.mercadio.filter.domain.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FilterRemoveSpecificWords implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Optional<String> extraArg;

    public FilterRemoveSpecificWords(Optional<String> extraArg) {
        this.extraArg = extraArg;
    }

    @Override
    public String filter(String text) {
        final String filteredText = replaceWords(text,extraArg);

        logger.info("\nRequest remove specific words filter: \n"+ filteredText);

        return filteredText;
    }

    private String replaceWords(String request, Optional<String> extraArg) {
        final List<String> wordsToReplace = (extraArg != null && extraArg.isPresent() && !extraArg.get().isEmpty()) ?
                Arrays.asList(extraArg.get().split(",")) : new ArrayList<>();

        return getReplacedString(request, wordsToReplace).trim();
    }

    private String getReplacedString(String request, List<String> wordsToReplace) {
        String replacedString = request;

        if (wordsToReplace.size() > 0) {
            for (String item : wordsToReplace) {
                replacedString = replacedString.replaceAll(item,"");
            }
        }
        return replacedString;
    }
}
