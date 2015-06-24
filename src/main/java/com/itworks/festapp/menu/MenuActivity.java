package com.itworks.festapp.menu;

import android.content.Intent;
import android.os.Bundle;
import com.itworks.festapp.ActionBarActivity;

public class MenuActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", intent.getIntExtra("id", -1));
        bundle.putString("text", intent.getStringExtra("text"));
        bundle.putBoolean("isItArtist", intent.getBooleanExtra("isItArtist", true));
        MenuFragment fragment = new MenuFragment();
        openFragment(bundle, fragment);
    }
}