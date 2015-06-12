package com.itworks.festapp.models;

public class NotificationModel extends BaseTimetable {

    public String title;

    public NotificationModel(int id, String title, int day, String start_time) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.start_time = start_time;
    }
}
