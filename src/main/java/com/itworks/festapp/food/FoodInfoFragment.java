package com.itworks.festapp.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.FoodModel;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class FoodInfoFragment extends Fragment implements View.OnClickListener {

    private final String no_link = "No link";
    private final String no_internet = "No internet connection";
    private final String foodInfoPref = "FoodInfoPref";
    private final String key = "id";
    private FoodModel foodModel;
    TextView title, about, location;
    RelativeLayout place;
    ImageView iw, linkF;
    private ImageLoader imageLoader;
    private JSONHelper jsonHelper;
    private SharedPreferences sharedpreferences;

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
        jsonHelper = new JSONHelper(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(foodInfoPref, Context.MODE_PRIVATE);
        if(null == foodModel){
            int id = sharedpreferences.getInt(key,-1);
            foodModel = getFoodModelById(id);
        }

        List<PlaceModel> places = jsonHelper.getPlacesFromJSON();
        PlaceModel coordinate = places.get(12);
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

        imageLoader = ImageLoader.getInstance();
        title = (TextView) v.findViewById(R.id.textView3);
        about = (TextView) v.findViewById(R.id.about);
        location = (TextView) v.findViewById(R.id.location);
        iw = (ImageView) v.findViewById(R.id.imageView3);

        if(!foodModel.link_facebook.isEmpty()) {
            imageLoader.displayImage("drawable://" + R.drawable.social_fb ,linkF);
        }
        int photo_id = getResources().getIdentifier("f" + foodModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + photo_id ,iw);
        title.setText(foodModel.title);
        location.setText(coordinate.name);
        about.setText(foodModel.about);
        setTypefaces();
        return v;
    }

    private void setTypefaces() {
        Typeface futura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/futura_condensed_medium.ttf");
        Typeface arial = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial_narrow.ttf");
        about.setTypeface(arial);
        title.setTypeface(futura);
        location.setTypeface(futura);
    }

    @Override
    public void onClick(View v) {
        openBrowser(foodModel.link_facebook);

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, foodModel.id);
        editor.apply();
    }

    private FoodModel getFoodModelById(int id){
        List<FoodModel> list = jsonHelper.getFoodFromJSON();
        for(FoodModel foodModel: list){
            if(foodModel.id == id){
                return foodModel;
            }
        }
        return new FoodModel();
    }

    private void openBrowser(String url){
        if(!url.isEmpty()){
            if(isNetworkAvailable()){
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }else{
                Toast.makeText(getActivity().getApplicationContext(), no_internet, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), no_link, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {
        super.onResume();
        place.setEnabled(true);
    }
}
