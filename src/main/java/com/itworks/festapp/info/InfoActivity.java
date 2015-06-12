package com.itworks.festapp.info;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.itworks.festapp.ActionBarActivity;
import com.itworks.festapp.R;

public class InfoActivity extends ActionBarActivity {
    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setActionBar();
        if (fm.findFragmentById(android.R.id.content) == null) {
            InfoListAdapterFragment list = new InfoListAdapterFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}