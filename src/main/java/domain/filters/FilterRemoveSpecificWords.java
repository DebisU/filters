package domain.filters;

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
        final String finalResult = replaceWords(text,extraArg);

        logger.info("\nRequest remove specific words filter: \n"+ finalResult);

        return finalResult;
    }

    private String replaceWords(String request, Optional<String> extraArg) {
        final List<String> wordsToReplace = (extraArg != null && extraArg.isPresent() && !extraArg.get().isEmpty()) ? Arrays.asList(extraArg.get().split(",")) : new ArrayList<>();
        request = getReplacedString(request, wordsToReplace);
        return request.trim();
    }

    private String getReplacedString(String request, List<String> wordsToReplace) {
        if (wordsToReplace.size() > 0) {
            for (String item : wordsToReplace) {
                request = request.replaceAll(item,"");
            }
        }
        return request;
    }
}
