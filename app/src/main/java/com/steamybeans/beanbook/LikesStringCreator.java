package com.steamybeans.beanbook;

public class LikesStringCreator {
    public String likesCalculator(int numLikes) {
        if (numLikes == 0) {
            return "";
        } else if (numLikes == 1) {
            return "1 like";
        } else {
            String numValue = String.valueOf(numLikes);
            return numValue + " likes";
        }
    }
}
