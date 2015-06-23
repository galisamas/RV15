package com.itworks.festapp.food;

import android.app.Activity;
import android.os.AsyncTask;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.models.FoodListItem;
import com.itworks.festapp.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapterFragment extends BaseListFragment {

    private List<FoodModel> food;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new AsyncTask<Void, Void, Void>() {
            List<FoodListItem> mItems;
            @Override
            protected Void doInBackground(Void... params) {
                mItems = new ArrayList<>();
                JSONRepository jsonRepository = new JSONRepository(getActivity());
                food = jsonRepository.getFoodFromJSON();
                for (FoodModel aFood : food) {
                    int photo_id = activity.getResources().getIdentifier("f" + aFood.id, "drawable", packageName);
                    mItems.add(new FoodListItem(photo_id, aFood.title));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setListAdapter(new FoodListAdapter(activity, mItems));
            }
        }.execute();
    }

}
