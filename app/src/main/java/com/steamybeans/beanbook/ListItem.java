package com.steamybeans.beanbook;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ListItem {

    private String user;
    private String post;
    private String time;
    private String likes;
    private String email;
    private String uid;

    public ListItem(String user, String post, String time, String likes, String email, String uid) {
        this.user = user;
        this.post = post;
        this.time = time;
        this.likes = likes;
        this.email = email;
        this.uid = uid;
    }

    public String getUser() {
        return user;
    }

    public String getPost() { return post; }

    public String getTime() { return time; }

    public String getLikes() { return likes; }

    public String getEmail() { return email; }

    public String getUid() {
        return uid;
    }

    public String getDisplayTimeSincePost() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.format(formatter);
        String postTime = this.time;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        String timeMessage = "";
        long secondsNumber;

        try {

            d1 = format.parse(postTime);
            d2 = format.parse(currentTime);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            secondsNumber = (Seconds.secondsBetween(dt1, dt2).getSeconds());

            if (secondsNumber < 60) {
                timeMessage = secondsNumber + " seconds ago";
            } else if (secondsNumber < 3600) {
                timeMessage = secondsNumber / 60 + " minutes ago";
            } else if (secondsNumber < 86400) {
                timeMessage = secondsNumber / 3600 + " hours ago";
            } else if (secondsNumber > 86400 ) {
                timeMessage = secondsNumber /86400 + " days ago";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeMessage;


    }

}
