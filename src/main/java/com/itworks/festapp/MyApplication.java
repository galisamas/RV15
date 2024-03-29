package com.itworks.festapp;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;


public class MyApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(config);

        ParseCrashReporting.enable(this);
        Parse.initialize(this, "82oOrqqYacCPkNSBhBy4cwTJ5NNbMkpPghYNvltZ", "vpgRI8ZHKK3T2p1jecM3wWpF0TKdyzjMmwapWdMr");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
