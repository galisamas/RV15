package com.itworks.festapp.models;

public class PlaceModel {
    public int id;
    public String name;
    public String where;
    public double latitude;
    public double longitude;

    public PlaceModel() {
    }

    public PlaceModel(int id, String name, String where, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.where = where;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
