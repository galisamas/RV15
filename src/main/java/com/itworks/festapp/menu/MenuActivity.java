package com.itworks.festapp.menu;

import android.content.Intent;
import android.os.Bundle;
import com.itworks.festapp.ActionBarActivity;

public class MenuActivity extends ActionBarActivity {

    private MenuFragment fragment;
    private int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", intent.getIntExtra("id", -1));
        bundle.putString("text", intent.getStringExtra("text"));
        bundle.putBoolean("isItArtist", intent.getBooleanExtra("isItArtist", true));
        fragment = new MenuFragment();
        openFragment(bundle, fragment);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(index == 0)
            fragment.onWindowFocusChanged(this);
        index++;
    }
}