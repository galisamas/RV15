package com.itworks.festapp.food;

import android.os.Bundle;
import com.itworks.festapp.ActionBarActivity;

public class FoodActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        openFragmentWithoutBundle(new FoodListAdapterFragment());

    }
}
