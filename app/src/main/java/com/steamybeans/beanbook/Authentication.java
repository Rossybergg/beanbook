package com.steamybeans.beanbook;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication {

    private EditText ETfullName;
    private EditText ETemail;
    private EditText ETpassword;


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
