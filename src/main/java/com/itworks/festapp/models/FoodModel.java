package com.itworks.festapp.models;

public class FoodModel {
    public int id;
    public String title;
    public String about;
    public String link_facebook;

    public FoodModel() {
    }

    public FoodModel(int id, String title, String about, String link_facebook) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.link_facebook = link_facebook;
    }
}
