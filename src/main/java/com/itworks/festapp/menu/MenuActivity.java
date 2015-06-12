package com.itworks.festapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MenuActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(android.R.id.content) == null) {
            MenuFragment menuFragment = new MenuFragment();
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putInt("id", intent.getIntExtra("id", -1));
            bundle.putString("text", intent.getStringExtra("text"));
            bundle.putBoolean("isItArtist", intent.getBooleanExtra("isItArtist", true));
            menuFragment.setArguments(bundle);
            fm.beginTransaction().add(android.R.id.content, menuFragment).commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onTrimMemory(TRIM_MEMORY_RUNNING_LOW);
    }

}