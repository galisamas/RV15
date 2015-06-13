package com.itworks.festapp.stages;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.ActionBarActivity;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.DateController;

import java.util.Calendar;

/**
 * Created by Naglis on 2015-02-01.
 */
public class StagesActivity extends ActionBarActivity implements View.OnClickListener {

    TextView tv1, tv2;
    private FragmentManager fm;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Drawable[] myDrawable;
    RelativeLayout b1, b2;
    private String[] tabTitles;
    protected int pagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_activity);
        fm = getSupportFragmentManager();
        setActionBar();
        tv1 = (TextView) findViewById(R.id.textView5);
        tv2 = (TextView) findViewById(R.id.textView2);
        Typeface futura = Typeface.createFromAsset(getAssets(), "fonts/futura_condensed_medium.ttf");
        tv1.setTypeface(futura);
        tv2.setTypeface(futura);

        b1 = (RelativeLayout) findViewById(R.id.button7);
        b2 = (RelativeLayout) findViewById(R.id.button8);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        tabTitles = getResources().getStringArray(R.array.tabs_names);
        TypedArray tabIcons = getResources().obtainTypedArray(R.array.tabs_icons);
        myDrawable = new Drawable[tabIcons.length()];
        for(int i=0;i<tabIcons.length();i++){
            myDrawable[i] = getResources().getDrawable(tabIcons.getResourceId(i, -1));
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        loadCustomPageAdapter(getDay(),1);
    }

    private int getDay(){ // TODO refactor iskelti DateHelper
        int day = 1;
        Calendar today = Calendar.getInstance();
        if(today.get(Calendar.MONTH) == DateController.FESTIVAL_MONTH){
            if((today.get(Calendar.DAY_OF_MONTH) == DateController.defaultCalendar(2).get(Calendar.DAY_OF_MONTH) &&
                    today.get(Calendar.HOUR_OF_DAY) > 10 )||
                    today.get(Calendar.DAY_OF_MONTH)-1 == DateController.defaultCalendar(2).get(Calendar.DAY_OF_MONTH)){
                day = 2;
            }
        }
        setDayButtonBackground(day);
        return day;
    }

    @Override
    public void onClick(View v) {
        int day;
        if(v.getId() == b1.getId()){
            day = 1;
        } else {
            day = 2;
        }
        setDayButtonBackground(day);
        pagePosition= mViewPager.getCurrentItem();
        loadCustomPageAdapter(day, pagePosition);
    }

    private void loadCustomPageAdapter(int day, int tabNumber) {
        mCustomPagerAdapter = new CustomPagerAdapter(fm, this, day);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(tabNumber);
    }

    private void setDayButtonBackground(int day){
        if(day ==1){
            b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_backgroud));
            b2.setBackgroundDrawable(null);
        }else{

            b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_backgroud));
            b1.setBackgroundDrawable(null);
        }
    }

    class CustomPagerAdapter extends FragmentStatePagerAdapter {

        Context mContext;
        private int day;

        public CustomPagerAdapter(FragmentManager fm, Context context, int day) {
            super(fm);
            mContext = context;
            this.day = day;
        }

        @Override
        public ListFragment getItem(int position) {
            StagesListAdapterFragment f = new StagesListAdapterFragment();
            f.setStage(position+1);
            f.setDay(day);
            return f;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder sb = new SpannableStringBuilder("  stage");
            myDrawable[position].setBounds(0, 0, myDrawable[position].getIntrinsicWidth(),
                    myDrawable[position].getIntrinsicHeight());
            ImageSpan span = new ImageSpan(myDrawable[position], ImageSpan.ALIGN_BASELINE);
            sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.list_separator)), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}

