package com.steamybeans.beanbook;

public class ListItem {

    private String user;
    private String post;
    private String time;

    public ListItem(String user, String post, String time) {
        this.user = user;
        this.post = post;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getPost() {
        return post;
    }

    public String getTime() { return time; }
}
