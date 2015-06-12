package com.itworks.festapp.food;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.models.FoodListItem;
import com.itworks.festapp.models.FoodModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapterFragment extends ListFragment {
    private String packageName;

    private List<FoodListItem> mItems;   // ListView items list
    private List<FoodModel> food;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<FoodListItem>();
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        food = jsonHelper.getFoodFromJSON();

        for(int i=0; i<food.size();i++) {
            int photo_id = this.getResources().getIdentifier("g" + food.get(i).id, "drawable", packageName);
            mItems.add(new FoodListItem(photo_id, food.get(i).title));
        }

        setListAdapter(new FoodListAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(getResources().getDrawable(R.color.list_separator));
        getListView().setDividerHeight(2);
        getListView().setBackgroundColor(Color.WHITE);
        ImageLoader imageLoader = ImageLoader.getInstance();
        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
        getListView().setOnScrollListener(listener);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FoodInfoFragment fragment = new FoodInfoFragment();
        fragment.setFoodModel(food.get(position));

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}
