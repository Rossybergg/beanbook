package com.steamybeans.beanbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication {

    public Authentication() {
    }


    public String encodeString(String string) {

        return string.replace(".", ",");
    }

    public boolean validEmail(String email) {

        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.matches();
        boolean result = false;
        if (matchFound) {
            result = true;
        }
        return result;
    }
}
