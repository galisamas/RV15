package com.itworks.festapp.models;

public class FoodModel extends BaseModel {

    public FoodModel() {
    }

    public FoodModel(int id, String title, String about, String link_facebook) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.link_facebook = link_facebook;
    }
}
