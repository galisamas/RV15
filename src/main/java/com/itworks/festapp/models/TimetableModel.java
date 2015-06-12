package com.itworks.festapp.models;

public class TimetableModel extends BaseTimetable {

    public int artistId;
    public int stageId;
    public boolean isItEmpty = true;

    public TimetableModel() {
    }

    public TimetableModel(int id, int artistId, int stageId, int day, String start_time, String end_time) {
        this.id = id;
        this.artistId = artistId;
        this.stageId = stageId;
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        isItEmpty = false;
    }
}
