package com.itworks.festapp.games;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.itworks.festapp.ActionBarActivity;

public class GamesActivity extends ActionBarActivity {

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setActionBar();
        if (fm.findFragmentById(android.R.id.content) == null) {
            GamesListAdapterFragment list = new GamesListAdapterFragment();
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putInt("id", intent.getIntExtra("id",-1));
            list.setArguments(bundle);
            list.setPackageName(this.getPackageName());
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}
