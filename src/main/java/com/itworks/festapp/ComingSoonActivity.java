package com.itworks.festapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ComingSoonActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        setContentView(R.layout.coming_soon_activity);
        Typeface futura = Typeface.createFromAsset(getAssets(), "fonts/futura_condensed_medium.ttf");
        TextView textView = (TextView) findViewById(R.id.item_title);
        textView.setTypeface(futura);
    }
}
