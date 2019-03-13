package com.steamybeans.beanbook;

public class CommentListitem {

    private String user;
    private String time;
    private String comment;

    public CommentListitem(String user, String time, String comment) {
        this.user = user;
        this.time = time;
        this.comment = comment;
    }

    public String getUser() { return user; }

    public String getTime() { return time; }

    public String getComment() { return comment; }
}
