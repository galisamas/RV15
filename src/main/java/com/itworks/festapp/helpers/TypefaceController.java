package com.itworks.festapp.helpers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceController {

    public Typeface getFutura() {
        return futura;
    }

    private final Typeface futura;
    private final Typeface arial;

    public TypefaceController(AssetManager asset) {
        futura = Typeface.createFromAsset(asset, "fonts/futura_condensed_medium.ttf");
        arial = Typeface.createFromAsset(asset, "fonts/arial_narrow.ttf");
    }

    public void setFutura (TextView view){
        view.setTypeface(futura);
    }

    public void setArial(TextView view){
        view.setTypeface(arial);
    }

    public void setArialBold(TextView view){
        view.setTypeface(arial, Typeface.BOLD);
    }

    public void setArialItalic(TextView view){
        view.setTypeface(arial, Typeface.ITALIC);
    }

}
