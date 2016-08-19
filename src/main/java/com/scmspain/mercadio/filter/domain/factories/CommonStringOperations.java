package com.scmspain.mercadio.filter.domain.factories;

import java.util.Arrays;
import java.util.List;

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
        for(int i =0; i < items.size(); i++) {
            if(inputString.toLowerCase().contains(items.get(i).toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String removeLastNewLine(String request) {
        if (request.endsWith("\n")) {
            request = request.substring(0,request.length()-1);
        }
        return request;
    }


}
