package com.itworks.festapp.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import com.itworks.festapp.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONRepository {

    private final String title = "title";
    private final String about = "about";
    private final String start_time = "start_time";
    private final String end_time = "end_time";
    private final String id = "id";
    private final String notification = "notification";
    private final String link_facebook = "link_facebook";
    private final String link_spotify = "link_spotify";
    private final String link_youtube = "link_youtube";
    private final String link_soundcloud = "link_soundcloud";
    private final String place_id = "places_id";
    private final String latitude = "latitude";
    private final String longitude = "longitude";
    private final String food = "food";
    private final String games = "games";
    private final String artists = "artists";
    private final String places = "places";
    private final String artistsJSON = "artists.json";
    private final String gamesJSON = "games.json";
    private final String foodJSON = "food.json";
    private final String placesJSON = "places.json";
    private final String day = "day";
    private final String artist_id = "artist_id";
    private final String stage_id = "stage_id";
    private final String origin = "origin";
    private final String timetable = "timetable";
    private final String timetableJSON = "timetable.json";
    private final String where = "where";
    private final String infoJSON = "info.json";
    private final String gameTimetableJSON = "gametimetable.json";
    private final String game_id = "game_id";
    private final String notificationsJSON = "notifications.json";
    private final String notifications = "notifications";
    AssetManager mngr;

    public JSONRepository(Context myContext) {
        mngr = myContext.getAssets();
    }

    public List<ArtistModel> getArtistsFromJSON(){
        JSONObject obj ;
        List<ArtistModel> formList= new ArrayList<>();
        try {
            obj = new JSONObject(loadJSONFromAsset(artistsJSON));
            JSONArray jsonArray = obj.getJSONArray(artists);
            ArtistModel artistModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString(this.title);
                String about = jsonObject.getString(this.about);
                                int id = jsonObject.getInt(this.id);
                boolean notification = jsonObject.getBoolean(this.notification);
                String link_facebook = jsonObject.getString(this.link_facebook);
                String link_spotify = jsonObject.getString(this.link_spotify);
                String link_youtube = jsonObject.getString(this.link_youtube);
                String link_scld = jsonObject.getString(link_soundcloud);
                String origin = jsonObject.getString(this.origin);

                artistModel =new ArtistModel(id,title, notification, about,
                        link_facebook,link_youtube,link_scld,link_spotify, origin);
                formList.add(artistModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<GameModel> getGamesFromJSON(){
        JSONObject obj ;
        List<GameModel> formList= new ArrayList<GameModel>();
        try {
            obj = new JSONObject(loadJSONFromAsset(gamesJSON));
            JSONArray jsonArray = obj.getJSONArray(games);
            GameModel gameModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int place_id = jsonObject.getInt(this.place_id);
                String title = jsonObject.getString(this.title);
                String about = jsonObject.getString(this.about);
                String link_facebook = jsonObject.getString(this.link_facebook);
                Boolean notification = jsonObject.getBoolean(this.notification);
                int id = jsonObject.getInt(this.id);

                gameModel =new GameModel(id, title, place_id, about, notification, link_facebook);
                formList.add(gameModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<GameTimetableModel> getGameTimetableFromJSON() {
        JSONObject obj ;
        List<GameTimetableModel> formList= new ArrayList<>();
        try {
            obj = new JSONObject(loadJSONFromAsset(gameTimetableJSON));
            JSONArray jsonArray = obj.getJSONArray(timetable);
            GameTimetableModel timetableModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(this.id);
                String time_s = jsonObject.getString(start_time);
                String time_e = jsonObject.getString(end_time);
                int gameId = jsonObject.getInt(game_id);
                int day = jsonObject.getInt(this.day);
                timetableModel =new GameTimetableModel(id, gameId, day, time_s, time_e);
                formList.add(timetableModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<NotificationModel> getNotificationsFromJSON() {
        JSONObject obj ;
        List<NotificationModel> notificationModels = new ArrayList<>();
        try {
            obj = new JSONObject(loadJSONFromAsset(notificationsJSON));
            JSONArray jsonArray = obj.getJSONArray(notifications);
            NotificationModel notificationModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(this.id);
                String time_s = jsonObject.getString(start_time);
                String title = jsonObject.getString(this.title);
                int day = jsonObject.getInt(this.day);
                notificationModel =new NotificationModel(id, title, day, time_s);
                notificationModels.add(notificationModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notificationModels;
    }

    public List<PlaceModel> getPlacesFromJSON(){
        JSONObject obj ;
        List<PlaceModel> formList= new ArrayList<>();
        try {
            obj = new JSONObject(loadJSONFromAsset(placesJSON));
            JSONArray jsonArray = obj.getJSONArray(places);
            PlaceModel placeModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString(this.title);
                String where = jsonObject.getString(this.where);
                int id = jsonObject.getInt(this.id);
                double lat = jsonObject.getDouble(latitude);
                double lng = jsonObject.getDouble(longitude);
                placeModel =new PlaceModel(id, title, where, lat, lng);
                formList.add(placeModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<TimetableModel> getTimetableFromJSON(){
        JSONObject obj ;
        List<TimetableModel> formList= new ArrayList<TimetableModel>();
        try {
            obj = new JSONObject(loadJSONFromAsset(timetableJSON));
            JSONArray jsonArray = obj.getJSONArray(timetable);
            TimetableModel timetableModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(this.id);
                String time_s = jsonObject.getString(start_time);
                String time_e = jsonObject.getString(end_time);
                int artistId = jsonObject.getInt(artist_id);
                int stageId = jsonObject.getInt(stage_id);
                int day = jsonObject.getInt(this.day);
                timetableModel =new TimetableModel(id, artistId, stageId, day, time_s, time_e);
                formList.add(timetableModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<FoodModel> getFoodFromJSON(){
        JSONObject obj ;
        List<FoodModel> formList= new ArrayList<FoodModel>();
        try {
            obj = new JSONObject(loadJSONFromAsset(foodJSON));
            JSONArray jsonArray = obj.getJSONArray(food);
            FoodModel foodModel;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String link_facebook = jsonObject.getString(this.link_facebook);
                String title = jsonObject.getString(this.title);
                String about = jsonObject.getString(this.about);
                int id = jsonObject.getInt(this.id);
                foodModel =new FoodModel(id, title, about, link_facebook);
                formList.add(foodModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public List<String> getRulesFromJSON(){
        return getInfoFromJSON(9);
    }

    public List<String> getInfoFromJSON(int jsonId) {
        JSONObject obj ;
        List<String> info = new ArrayList<>();
        try {
            obj = new JSONObject(loadJSONFromAsset(infoJSON));
            JSONArray jsonArray = obj.getJSONArray(getInfoNameById(jsonId));
            for(int j=0; j<jsonArray.length(); j++){
                info.add(jsonArray.getString(j));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public List<String> getMapLegendFromJSON(){
        List<PlaceModel> placesJson = getPlacesFromJSON();
        List<String> legend = new ArrayList<>();
        for(int i=0;i<placesJson.size();i++){
            legend.add(placesJson.get(i).id + ". " + placesJson.get(i).name);
        }
        return legend;
    }

    private String getInfoNameById(int id){
        String result  = "";
        switch (id){
            case 0:
                result = "drivemethere";
                break;
            case 2:
                result = "history";
                break;
            case 3:
                result = "dodont";
                break;
            case 4:
                result = "bring";
                break;
            case 5:
                result = "parking";
                break;
            case 6:
                result = "tickets";
                break;
            case 7:
                result = "howtouse";
                break;
            case 8:
                result = "itworks";
                break;
            case 9:
                result = "rules";
        }
        return result;
    }

    private String loadJSONFromAsset(String name) {
        String json ;
        try {

            InputStream is = mngr.open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
