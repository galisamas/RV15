package com.itworks.festapp.models;

public class GameModel {
    public int id;
    public String title;
    public int placeId;
    public String about;
    public Boolean notification;
    public String link_fb;

    public GameModel() {
    }

    public GameModel(int id, String title, int placeId, String about,
                     Boolean notification, String link_fb) {
        this.id = id;
        this.title = title;
        this.placeId = placeId;
        this.about = about;
        this.notification = notification;
        this.link_fb = link_fb;
    }
}