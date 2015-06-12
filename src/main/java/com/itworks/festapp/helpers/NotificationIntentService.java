package com.itworks.festapp.helpers;

import android.app.IntentService;
import android.content.Intent;
import com.activeandroid.query.Select;
import com.itworks.festapp.models.*;

import java.util.ArrayList;
import java.util.List;

public class NotificationIntentService extends IntentService {

    private JSONHelper jsonHelper;
    private List<PlaceModel> placeList;
    private List<ArtistModel> artistList;
    private List<GameModel> gameModels;
    private List<NotificationModel> notificationList;
    private Boolean onBoot;

    public NotificationIntentService() {
        super(NotificationIntentService.class.getName());
    }

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        onBoot = intent.getBooleanExtra("BOOT" ,false);
        if (getCount() == 0 || onBoot) {
            jsonHelper = new JSONHelper(this);
            placeList = jsonHelper.getPlacesFromJSON();
            gameModels = jsonHelper.getGamesFromJSON();
            artistList = jsonHelper.getArtistsFromJSON();
            notificationList = jsonHelper.getNotificationsFromJSON();
            if(getCount() == 0){
                setDefaultAlarms();
                setDefaultAlarmsToGames();
                setDefaultNotifications();
            }else if(onBoot){
                setDefaultAlarmsToGames();
                setDefaultNotifications();
                setAlarmsOnBoot();
            }
        }
    }

    private void setAlarmsOnBoot() {
        List<ArtistNotificationModel> sqlList = getAllSqlNotifications();
        for (ArtistNotificationModel anArtistList : sqlList) {
            if (anArtistList.notification) {
                ArtistModel artistModel = getArtistModelById(anArtistList.artistId);
                setArtistAlarms(artistModel);
            }
        }

    }

    private ArtistModel getArtistModelById(int id) {
        for(ArtistModel artistModel:artistList){
            if (artistModel.id == id)
                return artistModel;
        }
        return null;
    }

    private List<ArtistNotificationModel> getAllSqlNotifications() {
        return new Select().from(ArtistNotificationModel.class).execute();
    }

    private void setDefaultAlarms() {
        for (ArtistModel anArtistList : artistList) {
            ArtistNotificationModel artistNotificationModel = new ArtistNotificationModel(anArtistList.id, anArtistList.notification);
            artistNotificationModel.save();
            if (anArtistList.notification) {
                setArtistAlarms(anArtistList);
            }
        }
    }

    private void setArtistAlarms(ArtistModel anArtistList) {
        List<TimetableModel> timetables = getTimetableModels(anArtistList);
        for (int j = 0; j < timetables.size(); j++) {
            for (int k = 0; k < placeList.size();k++) {
                if (placeList.get(k).id == timetables.get(j).stageId) {
                    String where = placeList.get(k).where;
                    NotificationHelper.setAlarm(this, anArtistList.title, anArtistList.id, true, where, timetables.get(j));
                }
            }
        }
    }

    private void setDefaultNotifications() {
        for (NotificationModel notificationModel : notificationList) {
            NotificationHelper.setAlarm(this, notificationModel.title, notificationModel.id , false, "", notificationModel);
        }
    }

    private void setDefaultAlarmsToGames() {
        for (GameModel aGameList : gameModels) {
            if (aGameList.notification) {
                List<GameTimetableModel> timetables = getTimetableModels(aGameList);
                for (int j = 0; j < timetables.size(); j++) {
                    for (int k = 0; k < placeList.size();k++) {
                        if (placeList.get(k).id == aGameList.placeId) {
                            String where = placeList.get(k).where;
                            NotificationHelper.setAlarm(this, aGameList.title, aGameList.id , false, where, timetables.get(j));
                        }
                    }
                }
            }
        }
    }

    private List<TimetableModel> getTimetableModels(ArtistModel artistModel){
        List<TimetableModel> result = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonHelper.getTimetableFromJSON();
        for(int i =0;i<timetableModels.size();i++){
            if(timetableModels.get(i).artistId == artistModel.id){
                result.add(timetableModels.get(i));
            }
        }
        return result;
    }

    private List<GameTimetableModel> getTimetableModels(GameModel gameModel){
        List<GameTimetableModel> result = new ArrayList<>();
        List<GameTimetableModel> timetableModels = jsonHelper.getGameTimetableFromJSON();
        for(int i =0;i<timetableModels.size();i++){
            if(timetableModels.get(i).gameId == gameModel.id){
                result.add(timetableModels.get(i));
            }
        }
        return result;
    }

    private int getCount(){
        return new Select().from(ArtistNotificationModel.class).count();
    }

}
