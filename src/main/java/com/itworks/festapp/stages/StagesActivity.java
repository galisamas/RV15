package com.itworks.festapp.stages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.ActionBarActivity;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.CustomTypefaceSpan;
import com.itworks.festapp.helpers.DateController;
import com.itworks.festapp.helpers.TypefaceController;

public class StagesActivity extends ActionBarActivity implements View.OnClickListener {

    TextView tv1, tv2;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    RelativeLayout b1, b2;
    private String[] tabTitles;
    protected int pagePosition;
    private TypefaceController typefaceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_activity);
        setActionBar();
        tv1 = (TextView) findViewById(R.id.textView5);
        tv2 = (TextView) findViewById(R.id.textView2);
        typefaceController = new TypefaceController(getAssets());
        typefaceController.setFutura(tv1);
        typefaceController.setFutura(tv2);

        b1 = (RelativeLayout) findViewById(R.id.button7);
        b2 = (RelativeLayout) findViewById(R.id.button8);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        tabTitles = getResources().getStringArray(R.array.tabs_names);


        mViewPager = (ViewPager) findViewById(R.id.pager);
        loadCustomPageAdapter(getDay(),1);
    }

    private int getDay(){
        int day = DateController.getDayForStage();
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
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this, day);
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
            SpannableStringBuilder sb = new SpannableStringBuilder(tabTitles[position]);
            TypefaceSpan futuraSpan = new CustomTypefaceSpan(typefaceController.getFutura());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.list_separator));

            sb.setSpan(futuraSpan, 0, sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(colorSpan, 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}

