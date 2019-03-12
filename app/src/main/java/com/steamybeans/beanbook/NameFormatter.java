package com.steamybeans.beanbook;

public class NameFormatter {
    public static String capitalize(String name) {
        String temp = "";
        String capitalizedName = "";
        // Split string to segments either side of space(s)
        String[] segmentsAboutSpaces = name.split("\\s");
        for (String s : segmentsAboutSpaces) {
            String capitalLetter = s.substring(0, 1);
            String lowerCaseLetters = s.substring(1);
            temp += capitalLetter.toUpperCase() + lowerCaseLetters + " ";
        }
        temp = temp.trim();

        // Split string to segments either side of space(s)
        String[] segmentsAboutHyphens = temp.split("-");
        for (String s : segmentsAboutHyphens) {
            String capitalLetter = s.substring(0, 1);
            String lowerCaseLetters = s.substring(1);
            capitalizedName += capitalLetter.toUpperCase() + lowerCaseLetters + "-";
        }

        // Remove extraneous hyphen appended to name by last for loop
        capitalizedName = capitalizedName.substring(0, capitalizedName.length() - 1);
        return capitalizedName;
    }
}
