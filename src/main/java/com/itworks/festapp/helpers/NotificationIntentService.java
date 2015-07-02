package com.itworks.festapp.helpers;

import android.app.IntentService;
import android.content.Intent;
import com.activeandroid.query.Select;
import com.itworks.festapp.models.*;

import java.util.List;

public class NotificationIntentService extends IntentService {

    private List<PlaceModel> placeList;
    private List<ArtistModel> artistList;
    private List<GameModel> gameModels;
    private List<NotificationModel> notificationList;
    private ModelsController modelsController;

    public NotificationIntentService() {
        super(NotificationIntentService.class.getName());
    }

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Boolean onBoot = intent.getBooleanExtra("BOOT", false);
        if (getCount() == 0 || onBoot) {
            JSONRepository jsonRepository = new JSONRepository(this);
            placeList = jsonRepository.getPlacesFromJSON();
            gameModels = jsonRepository.getGamesFromJSON();
            artistList = jsonRepository.getArtistsFromJSON();
            notificationList = jsonRepository.getNotificationsFromJSON();
            modelsController = new ModelsController(getApplicationContext());
            if(getCount() == 0){
                setDefaultAlarms();
            }else if(onBoot){
                setAlarmsOnBoot();
            }
            setDefaultNotifications();
            setDefaultAlarmsToGames();
        }
    }

    private void setAlarmsOnBoot() {
        List<ArtistNotificationModel> sqlList = getAllSqlNotifications();
        for (ArtistNotificationModel anArtistList : sqlList) {
            if (anArtistList.notification) {
                ArtistModel artistModel = modelsController.getArtistModelById(anArtistList.artistId);
                setArtistAlarms(artistModel);
            }
        }
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
        List<TimetableModel> timetables = modelsController.getTimetableModelsByArtistId(anArtistList.id);
        for (TimetableModel timetable : timetables) {
            for (PlaceModel aPlaceList : placeList) {
                if (aPlaceList.id == timetable.stageId) {
                    String where = aPlaceList.where;
                    NotificationController.setAlarm(this, anArtistList.title, anArtistList.id, true, where, timetable);
                }
            }
        }
    }

    private void setDefaultNotifications() {
        for (NotificationModel notificationModel : notificationList) {
            NotificationController.setAlarm(this, notificationModel.title, notificationModel.id, false, "", notificationModel);
        }
    }

    private void setDefaultAlarmsToGames() {
        for (GameModel aGameList : gameModels) {
            if (aGameList.notification) {
                List<GameTimetableModel> timetables = modelsController.getGameTimetableModelsByGameId(aGameList.id);
                for (GameTimetableModel timetable : timetables) {
                    for (PlaceModel aPlaceList : placeList) {
                        if (aPlaceList.id == aGameList.placeId) {
                            String where = aPlaceList.where;
                            NotificationController.setAlarm(this, aGameList.title, aGameList.id, false, where, timetable);
                        }
                    }
                }
            }
        }
    }

    private int getCount(){
        return new Select().from(ArtistNotificationModel.class).count();
    }

}
