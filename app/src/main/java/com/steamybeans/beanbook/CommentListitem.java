package com.steamybeans.beanbook;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public String getDisplayTimeSinceComment() {
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
                if (secondsNumber < 120) {
                    timeMessage = "1 minute ago";
                } else {
                    timeMessage = secondsNumber / 60 + " minutes ago";
                }
            } else if (secondsNumber < 86400) {
                if (secondsNumber < 7200 ) {
                    timeMessage = "1 hour ago";
                } else {
                    timeMessage = secondsNumber / 3600 + " hours ago";
                }
            } else if (secondsNumber > 86400 ) {
                if (secondsNumber < 172800) {
                    timeMessage = "1 day ago";
                } else {
                    timeMessage = secondsNumber /86400 + " days ago";
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeMessage;


    }

}
