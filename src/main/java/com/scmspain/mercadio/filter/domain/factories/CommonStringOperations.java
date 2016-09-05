package com.scmspain.mercadio.filter.domain.factories;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonStringOperations {
    public static List<String> splitParagraphs(String request) {
        return Arrays.asList(request.split("\n"));
    }

    public static String htmlToText(String request) {
        String result = request;

        result = result.replaceAll("<p>","");
        result = result.replaceAll("</p>","");
        result = result.replaceAll("<br />","\n");
        result = result.replaceAll("<br/>","\n");

        return result;
    }

    public static String textToHtml(String request) {
        String result = request;
        result = result.replaceAll("\n","<br />");
        if (!result.isEmpty()) {
            result += "</p>";
        }

        return result;
    }

    public static boolean checkIfStringContainsItemFromList(String inputString, List<String> items) {
        for (String item : items) {
            if (inputString.toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfStringMatchesItemFromList(String inputString, List<String> items) {
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

    public static String removeLastNewLine(String request) {
        if (request.endsWith("\n")) {
            request = request.substring(0,request.length() - 1);
        }
        return request;
    }

    public static List<String> getAllPrepositions() {
        return Arrays.asList(" a "," ante "," bajo "," cabe "," con "," contra "," de "," desde "," en "," entre ",
                " hacia "," hasta "," para "," por "," según "," segun "," sin "," so "," sobre "," tras ");
    }

    public static List<String> getMostCommonSpanishWords() {
        return Arrays.asList(" la "," que "," el "," los ", " las "," se "," del "," un ",
                " su "," se "," es "," no "," si ");
    }

    public static List<String> getTimeUnits() {
        return Arrays.asList("horas", "minutos",
                "segundos", "hours", "años");
    }

    public static List<String> getSpecificationWords() {
        return Arrays.asList("3D", "HD","lúmens","wi-fi","accesorios","universal",
                "regulable","regulables","tarjeta sd","idiomas");
    }

    public static List<String> getAbbreviatedUnitsOfMeasure() {
        return Arrays.asList("mhz","ghz","hz","m²","m³","km","gb","mb","tb","mpx","cm","mm");
    }
}
