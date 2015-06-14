package com.itworks.festapp.info;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.itworks.festapp.ActionBarActivity;

public class InfoActivity extends ActionBarActivity {
    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setActionBar();
        openFragmentWithoutBundle(new InfoListAdapterFragment());
    }
}