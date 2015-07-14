package com.itworks.festapp.models;

public class FoodListItem {
    public final int photoId;
    public final String name;
    public final int id;

    public FoodListItem(int id, int photoId, String name) {
        this.id = id;
        this.photoId = photoId;
        this.name = name;
    }
}
