package com.itworks.festapp.models;

public class FoodModel {
    public int id;
    public String photo_name;
    public String title;
    public String about;
    public String link_facebook;

    public FoodModel() {
    }

    public FoodModel(int id, String photo_name, String title, String about, String link_facebook) {
        this.id = id;
        this.photo_name = photo_name;
        this.title = title;
        this.about = about;
        this.link_facebook = link_facebook;
    }
}
