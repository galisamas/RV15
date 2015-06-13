package com.itworks.festapp.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.itworks.festapp.R;
import com.itworks.festapp.menu.MenuActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setVibrate(new long[]{0, 500, 100, 500, 0});
        mBuilder.setLights(Color.GREEN, 1000, 3000);
        mBuilder.setAutoCancel(true);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setSmallIcon(R.drawable.ic_stat);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        String name = intent.getStringExtra("name");
        if(!intent.getBooleanExtra("isItGeneral", false)){
            mBuilder.setContentTitle(name);
            mBuilder.setTicker("Po 10 minučių " + intent.getStringExtra("where")); // TODO iskelt tuos where
            mBuilder.setContentText("Po 10 minučių " + intent.getStringExtra("where"));
        }else{
            mBuilder.setContentTitle("Radistai Village 2015");
            mBuilder.setTicker(name);
            mBuilder.setContentText(name);
        }
        int id = intent.getIntExtra("id",-1);
        /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(context, MenuActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("isItArtist", intent.getBooleanExtra("isItArtist", true));
        resultIntent.putExtra("id", id);
        if(intent.getBooleanExtra("isItGeneral", false)){
            resultIntent.putExtra("text", name);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MenuActivity.class);

      /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      /* notificationID allows you to delete the notification later on. */
        int notificationId = -2;
        if(intent.getBooleanExtra("isItArtist", true))
            notificationId = id;

        if(null != name)
            mNotificationManager.notify(notificationId, mBuilder.build());
        else{
            Intent serviceIntent = new Intent(Intent.ACTION_SYNC, null, context, NotificationIntentService.class);
            serviceIntent.putExtra("BOOT",true);
            context.startService(serviceIntent);
        }
    }
}
