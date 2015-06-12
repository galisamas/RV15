package com.itworks.festapp.models;

public class GameTimetableModel extends BaseTimetable {

    public int gameId;

    public GameTimetableModel(int id, int gameId, int day, String start_time, String end_time) {
        this.id = id;
        this.gameId = gameId;
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
