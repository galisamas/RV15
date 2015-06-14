package com.itworks.festapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class ActionBarActivity extends FragmentActivity {

    private FragmentManager fm = getSupportFragmentManager();

    public void setActionBar() {
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        ImageView back = (ImageView) mCustomView.findViewById(R.id.imageView1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fm.getBackStackEntryCount() == 0) {
                    ActionBarActivity.this.finish();
                }
                else {
                    fm.popBackStack();
                }
            }
        });
        ImageView home = (ImageView) mCustomView.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBarActivity.this.finish();
            }
        });
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setCustomView(mCustomView, layoutParams);
    }

    public void openFragmentWithoutBundle(BaseListFragment fragment){
        openFragment(null, fragment);
    }

    public void openFragment(Bundle bundle, BaseListFragment fragment){
        if (fm.findFragmentById(android.R.id.content) == null) {
            if(bundle != null){
                fragment.setArguments(bundle);
            }
            fragment.setPackageName(this.getPackageName());
            fm.beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
