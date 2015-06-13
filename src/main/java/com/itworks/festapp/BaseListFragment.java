package com.itworks.festapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import com.itworks.festapp.artists.ArtistInfoFragment;
import com.itworks.festapp.food.FoodInfoFragment;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.FoodModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class BaseListFragment extends ListFragment {

    protected String packageName;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(getResources().getDrawable(R.color.list_separator));
        getListView().setDividerHeight(2);
        getListView().setFastScrollEnabled(true);
        ImageLoader imageLoader = ImageLoader.getInstance();
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, false, true);
        getListView().setOnScrollListener(listener);
    }

    public void openInfo(ArtistModel artistModel){ // TODO refactor
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ArtistInfoFragment fragment = new ArtistInfoFragment();
        fragment.setArtistModel(artistModel);

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void openInfo(FoodModel foodModel){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FoodInfoFragment fragment = new FoodInfoFragment();
        fragment.setFoodModel(foodModel);

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
