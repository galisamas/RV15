package com.itworks.festapp.food;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.itworks.festapp.ActionBarActivity;

public class FoodActivity extends ActionBarActivity {

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setActionBar();
        if (fm.findFragmentById(android.R.id.content) == null) {
            FoodListAdapterFragment list = new FoodListAdapterFragment();
            list.setPackageName(this.getPackageName());
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}
