package com.steamybeans.beanbook;

public class ListItem {

    private String user;
    private String post;
    private String time;
    private String likes;
    private String email;

    public ListItem(String user, String post, String time, String likes, String email) {
        this.user = user;
        this.post = post;
        this.time = time;
        this.likes = likes;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public String getPost() { return post; }

    public String getTime() { return time; }

    public String getLikes() { return likes; }

    public String getEmail() { return email; }

}
