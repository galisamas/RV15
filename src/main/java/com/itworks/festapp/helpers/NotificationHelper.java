package com.itworks.festapp.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.itworks.festapp.models.BaseTimetable;
import com.itworks.festapp.models.NotificationModel;

import java.util.Calendar;

public class NotificationHelper {

    public static void setAlarm(Context context, String title, int id, boolean isItArtist, String where, BaseTimetable timetable) {

        String timeFormat;
        if(timetable instanceof NotificationModel){
            timeFormat = timetable.day + "/" + timetable.start_time;
        }else{
            timeFormat = DateHelper.minusTenMinutes(timetable.day + "/" + timetable.start_time);
        }
        int hour = DateHelper.getHour(timeFormat);
        int minute = DateHelper.getMinutes(timeFormat);
        int day = DateHelper.defaultCalendar(DateHelper.getDay(timeFormat)).get(Calendar.DAY_OF_MONTH);

        if(hour<7){
            day++;
        }
        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, DateHelper.FESTIVAL_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);
        if(calendar.after(today)) {
            Log.d("NOTIFICATION", where + " " + title + " " + hour + " " + minute); // TODO log
            Intent intent = new Intent(context , NotificationReceiver.class);
            intent.putExtra("name", title);
            intent.putExtra("id", id);
            intent.putExtra("where", where);
            intent.putExtra("isItArtist", isItArtist);
            if(timetable instanceof NotificationModel){
                intent.putExtra("isItGeneral", true);
            }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void cancelAlarm(Context context, int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, new Intent(context , NotificationReceiver.class), 0);
        alarmManager.cancel(pendingIntent);
    }
}
