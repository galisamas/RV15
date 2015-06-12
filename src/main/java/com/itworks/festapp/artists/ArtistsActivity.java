package com.itworks.festapp.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.itworks.festapp.ActionBarActivity;

/**
 * Created by Naglis on 2015-01-31.
 */
public class ArtistsActivity extends ActionBarActivity {

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        fm = getSupportFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            ArtistsListAdapterFragment list = new ArtistsListAdapterFragment();
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putInt("id", intent.getIntExtra("id",-1));
            list.setArguments(bundle);
            list.setPackageName(this.getPackageName());
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}