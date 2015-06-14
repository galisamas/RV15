package com.itworks.festapp.models;

public class GameModel extends BaseModel{

    public int placeId;
    public Boolean notification;

    public GameModel() {
    }

    public GameModel(int id, String title, int placeId, String about,
                     Boolean notification, String link_fb) {
        this.id = id;
        this.title = title;
        this.placeId = placeId;
        this.about = about;
        this.notification = notification;
        this.link_facebook = link_fb;
    }
}