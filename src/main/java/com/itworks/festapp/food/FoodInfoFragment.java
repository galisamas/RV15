package com.itworks.festapp.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.BrowserHelper;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.helpers.ModelsHelper;
import com.itworks.festapp.helpers.TypefaceHelper;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.FoodModel;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class FoodInfoFragment extends Fragment implements View.OnClickListener {

    private final String foodInfoPref = "FoodInfoPref";
    private final String key = "id";
    private final int foodCourtId = 12;
    private FoodModel foodModel;
    TextView title, about, location;
    RelativeLayout place;
    ImageView iw, linkF;
    private SharedPreferences sharedpreferences;
    private BrowserHelper browserHelper;

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_info_fragment, container, false);
        place = (RelativeLayout) v.findViewById(R.id.place);
        place.setEnabled(true);
        linkF = (ImageView) v.findViewById(R.id.imageFb);
        linkF.setOnClickListener(this);
        browserHelper = new BrowserHelper(getActivity());
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        ModelsHelper modelsHelper = new ModelsHelper(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(foodInfoPref, Context.MODE_PRIVATE);
        if(null == foodModel){
            int id = sharedpreferences.getInt(key,-1);
            foodModel = modelsHelper.getFoodModelById(id);
        }

        List<PlaceModel> places = jsonHelper.getPlacesFromJSON();
        ImageLoader imageLoader = ImageLoader.getInstance();
        PlaceModel coordinate = places.get(foodCourtId);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TerritoryActivity.class);
                intent.putExtra("place_latitude", coordinate.latitude);
                intent.putExtra("place_longitude", coordinate.longitude);
                intent.putExtra("name", foodModel.title);
                FoodInfoFragment.this.startActivity(intent);
                place.setEnabled(false);
            }
        });

        title = (TextView) v.findViewById(R.id.textView3);
        about = (TextView) v.findViewById(R.id.about);
        location = (TextView) v.findViewById(R.id.location);
        iw = (ImageView) v.findViewById(R.id.imageView3);

        if(!foodModel.link_facebook.isEmpty()) {
            imageLoader.displayImage("drawable://" + R.drawable.social_fb, linkF);
        }
        int photo_id = getResources().getIdentifier("f" + foodModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + photo_id, iw);
        title.setText(foodModel.title);
        location.setText(coordinate.name);
        about.setText(foodModel.about);
        setTypefaces();
        return v;
    }

    private void setTypefaces() {
        TypefaceHelper typefaceHelper = new TypefaceHelper(getActivity().getAssets());
        typefaceHelper.setFutura(title);
        typefaceHelper.setFutura(location);
        typefaceHelper.setArial(about);
    }

    @Override
    public void onClick(View v) {
        browserHelper.openBrowser(foodModel.link_facebook);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, foodModel.id);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        place.setEnabled(true);
    }
}
