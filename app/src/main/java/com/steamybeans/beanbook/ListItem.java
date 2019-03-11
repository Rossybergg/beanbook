package com.steamybeans.beanbook;

public class ListItem {

    private String user;
    private String post;

    public ListItem(String user, String post) {
        this.user = user;
        this.post = post;
    }

    public String getUser() {
        return user;
    }

    public String getPost() {
        return post;
    }
}
