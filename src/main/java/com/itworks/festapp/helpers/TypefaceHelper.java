package com.itworks.festapp.helpers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceHelper {

    private final Typeface futura;
    private final Typeface arial;

    public TypefaceHelper(AssetManager asset) {
        futura = Typeface.createFromAsset(asset, "fonts/futura_condensed_medium.ttf");
        arial = Typeface.createFromAsset(asset, "fonts/arial_narrow.ttf");
    }

    public void setFutura (TextView view){
        view.setTypeface(futura);
    }

    public void setArial(TextView view){
        view.setTypeface(arial);
    }
}
