package com.itworks.festapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.itworks.festapp.helpers.PhotoHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SplashScreen extends Fragment {

    ImageView photo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.splash_screen, container, false);
        photo = (ImageView) v.findViewById(R.id.loading_screen);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + R.drawable.loading_screen ,photo);
        YoYo.with(Techniques.Tada)
                .duration(1700)
                .playOn(v.findViewById(R.id.loading_screen));
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PhotoHelper.destroyDrawable(photo);
    }
}