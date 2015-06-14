package com.itworks.festapp.food;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.models.FoodListItem;
import com.itworks.festapp.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapterFragment extends BaseListFragment {

    private List<FoodModel> food;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<FoodListItem> mItems = new ArrayList<>();
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        food = jsonRepository.getFoodFromJSON();
        for (FoodModel aFood : food) {
            int photo_id = this.getResources().getIdentifier("g" + aFood.id, "drawable", packageName);
            mItems.add(new FoodListItem(photo_id, aFood.title));
        }
        setListAdapter(new FoodListAdapter(getActivity(), mItems));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        openInfo(food.get(position), new FoodInfoFragment());
    }
}
