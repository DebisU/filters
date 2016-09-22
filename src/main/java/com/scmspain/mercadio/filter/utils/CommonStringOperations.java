package com.scmspain.mercadio.filter.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CommonStringOperations {
    static List<String> splitParagraphs(String request) {
        return Arrays.asList(request.split("\n"));
    }

    static String htmlToText(String request) {
        String result = request;

        result = result.replaceAll("<p>","");
        result = result.replaceAll("</p>","");
        result = result.replaceAll("<br />","\n");
        result = result.replaceAll("<br/>","\n");
        result = mercadioLineFeed(result);

        return result;
    }

    static String textToHtml(String request) {
        String result = request;
        result = result.replaceAll("\n","<br />");
        if (!result.isEmpty()) {
            result += "</p>";
        }

        return result;
    }

    static boolean checkIfStringContainsItemFromList(String inputString, List<String> items) {
        for (String item : items) {
            if (inputString.toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    static boolean checkIfStringMatchesItemFromList(String inputString, List<String> items) {
        for (String item : items) {
            final Pattern actualItemToMatch = Pattern.compile(
                    "((.*?" + item.toLowerCase() + "[,. ].*?).)*", Pattern.CASE_INSENSITIVE);
            final Matcher myMatcher = actualItemToMatch.matcher(inputString);

            if (myMatcher.matches()) {
                return true;
            }
        }
        return false;
    }

    static String removeLastNewLine(String request) {
        String lastLine = request;

        if (lastLine.endsWith("\n")) {
            lastLine = lastLine.substring(0,lastLine.length() - 1);
        }
        return lastLine;
    }

    static String mercadioLineFeed(String request) {
        return request.replaceAll("&lt;div&gt;","\n");
    }

    static List<String> getAllPrepositions() {
        return Arrays.asList(" a "," ante "," bajo "," cabe "," con "," contra "," de "," desde "," en "," entre ",
                " hacia "," hasta "," para "," por "," según "," segun "," sin "," so "," sobre "," tras ");
    }

    static List<String> getMostCommonSpanishWords() {
        return Arrays.asList(" la "," que "," el "," los ", " las "," se "," del "," un ",
                " su "," se "," es "," no "," si ");
    }

    static List<String> getMeasureUnits() {
        return Arrays.asList("litros");
    }

    static List<String> getTimeUnits() {
        return Arrays.asList("horas", "minutos",
                "segundos", "hours", "años");
    }

    static List<String> getSpecificationWords() {
        return Arrays.asList("3D", "HD","lúmens","wi-fi","accesorios","universal",
                "regulable","regulables","tarjeta sd","idiomas");
    }

    static List<String> getAbbreviatedUnitsOfMeasure() {
        return Arrays.asList("mhz","ghz","hz","m²","m³","km","gb","mb","tb","mpx","cm","mm","KvA");
    }
}
