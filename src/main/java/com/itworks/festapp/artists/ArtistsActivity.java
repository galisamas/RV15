package com.itworks.festapp.artists;

import android.content.Intent;
import android.os.Bundle;
import com.itworks.festapp.ActionBarActivity;

public class ArtistsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", intent.getIntExtra("id", -1));
        OpenFragment(bundle, new ArtistsListAdapterFragment());

    }
}