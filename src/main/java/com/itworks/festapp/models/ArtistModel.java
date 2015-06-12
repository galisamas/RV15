package com.itworks.festapp.models;

public class ArtistModel {
    public int id;
    public String title;
    public boolean notification;
    public String about;
    public String link_facebook;
    public String link_youtube;
    public String link_soundcloud;
    public String link_spotify;
    public String origin;

    public ArtistModel() {
    }

    public ArtistModel(int id, String title, boolean notification, String about, String link_facebook, String link_youtube,
                       String link_soundcloud, String link_spotify, String origin) {
        this.id = id;
        this.title = title;
        this.notification = notification;
        this.about = about;
        this.link_facebook = link_facebook;
        this.link_youtube = link_youtube;
        this.link_soundcloud = link_soundcloud;
        this.link_spotify = link_spotify;
        this.origin = origin;
    }
}
