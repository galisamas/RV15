package com.itworks.festapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import com.itworks.festapp.helpers.NotificationIntentService;
import com.itworks.festapp.menu.MenuActivity;

public class MyActivity extends FragmentActivity {

    protected MyApplication app;
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);

        int myTimer = 1800;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                app = (MyApplication) getApplication();
                setDefaultNotifications();
                Intent intent = new Intent(MyActivity.this, MenuActivity.class);
                MyActivity.this.startActivity(intent);
                MyActivity.this.finish();
            }
        };
        handler.postDelayed(runnable
        , myTimer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        this.finish();
    }
    private void setDefaultNotifications(){
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, NotificationIntentService.class);
        startService(intent);
    }

}
