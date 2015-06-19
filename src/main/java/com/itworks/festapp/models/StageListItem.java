package com.itworks.festapp.models;

public class StageListItem {
    public final int colorId;
    public final String photoId;
    public final String name;
    public final String time;
    public final String place;

    public StageListItem(int colorId, String photoId, String name, String time, String place) {
        this.colorId = colorId;
        this.photoId = photoId;
        this.name = name;
        this.time = time;
        this.place = place;
    }
}
