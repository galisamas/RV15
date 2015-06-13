package com.itworks.festapp.games;

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
import com.itworks.festapp.helpers.DateHelper;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.GameModel;
import com.itworks.festapp.models.GameTimetableModel;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GameInfoFragment extends Fragment implements View.OnClickListener{

    private final String no_link = "No link";
    private final String no_internet = "No internet connection";
    private final String gameInfoPref = "GameInfoPref";
    private final String key = "id";
    private GameModel gameModel;
    TextView location, title, about, description, description2;
    ImageView iw, linkF;
    RelativeLayout place, second;
    private ImageLoader imageLoader;
    private JSONHelper jsonHelper;
    private SharedPreferences sharedpreferences;

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_info_fragment, container, false);
        location = (TextView) v.findViewById(R.id.location);
        place = (RelativeLayout) v.findViewById(R.id.place);
        title = (TextView) v.findViewById(R.id.textView3);
        about = (TextView) v.findViewById(R.id.about);
        description = (TextView) v.findViewById(R.id.description);
        iw = (ImageView) v.findViewById(R.id.imageView3);
        description2 = (TextView) v.findViewById(R.id.description2);
        second = (RelativeLayout) v.findViewById(R.id.second);
        imageLoader = ImageLoader.getInstance();
        jsonHelper = new JSONHelper(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(gameInfoPref, Context.MODE_PRIVATE);
        if(null == gameModel){
            int id = sharedpreferences.getInt(key,-1);
            gameModel = getGameModelById(id);
        }
        PlaceModel placeModel = getPlaceModelById();
        if(!gameModel.link_fb.isEmpty()) {
            linkF = (ImageView) v.findViewById(R.id.imageFb);
            imageLoader.displayImage("drawable://" + R.drawable.social_fb, linkF);
            linkF.setOnClickListener(this);
        }
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TerritoryActivity.class);
                intent.putExtra("place_latitude", placeModel.latitude);
                intent.putExtra("place_longitude", placeModel.longitude); // TODO uzdeti disable kai paspaudzia karta ant mapo
                intent.putExtra("name", gameModel.title);
                GameInfoFragment.this.startActivity(intent);
            }
        });
        int photo_id = getResources().getIdentifier("p" + gameModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + photo_id, iw);
        location.setText(placeModel.name);
        List<GameTimetableModel> timetables = getGameTimetableModels();
        description.setText(DateHelper.convertDate(timetables.get(0).day + "/" + timetables.get(0).start_time, timetables.get(0).day + "/" + timetables.get(0).end_time));
        if (timetables.size() != 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.BELOW, R.id.description);
            params.setMargins(0, -12, 0, 0);
            second.setLayoutParams(params);
            second.requestLayout();
            description2.setText(DateHelper.convertDate(timetables.get(1).day + "/" + timetables.get(1).start_time, timetables.get(1).day + "/" + timetables.get(1).end_time));
        }
        title.setText(gameModel.title);
        about.setText(gameModel.about);
        setTypefaces();
        return v;
    }

    private PlaceModel getPlaceModelById(){
        List<PlaceModel> placeModels = jsonHelper.getPlacesFromJSON();
        for(int i =0;i<placeModels.size();i++){
            if(placeModels.get(i).id == gameModel.placeId){
                return placeModels.get(i);
            }
        }
        return new PlaceModel();
    }

    private List<GameTimetableModel> getGameTimetableModels() {
        List<GameTimetableModel> result = new ArrayList<>();
        List<GameTimetableModel> timetableModels = jsonHelper.getGameTimetableFromJSON();
        for(int i =0;i<timetableModels.size();i++){
            if(timetableModels.get(i).gameId == gameModel.id){
                result.add(timetableModels.get(i));
            }
        }
        return result;
    }

    private void setTypefaces() {
        Typeface futura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/futura_condensed_medium.ttf");
        Typeface arial = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial_narrow.ttf");
        about.setTypeface(arial);
        title.setTypeface(futura);
        description.setTypeface(futura);
        location.setTypeface(futura);
        description2.setTypeface(futura);
    }

    @Override
    public void onClick(View v) {
        openBrowser(gameModel.link_fb);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, gameModel.id);
        editor.apply();
    }

    private GameModel getGameModelById(int id){
        List<GameModel> list = jsonHelper.getGamesFromJSON();
        for(GameModel gameModel: list){
            if(gameModel.id == id){
                return gameModel;
            }
        }
        return new GameModel();
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
}
