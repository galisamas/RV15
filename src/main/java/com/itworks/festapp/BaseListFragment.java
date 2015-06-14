package com.itworks.festapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import com.itworks.festapp.models.BaseModel;
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
        getListView().setBackgroundColor(Color.WHITE);
        ImageLoader imageLoader = ImageLoader.getInstance();
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, false, true);
        getListView().setOnScrollListener(listener);
    }

    public void openInfo(BaseModel baseModel, BaseFragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragment.setBaseModel(baseModel);

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
