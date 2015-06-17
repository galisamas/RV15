package com.itworks.festapp.games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.BaseFragment;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.*;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.BaseModel;
import com.itworks.festapp.models.GameModel;
import com.itworks.festapp.models.GameTimetableModel;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class GameInfoFragment extends BaseFragment implements View.OnClickListener{

    private final String gameInfoPref = "GameInfoPref";
    private final String key = "id";
    private GameModel gameModel;
    TextView location, title, about, description, description2;
    ImageView iw, linkF;
    RelativeLayout place, second;
    private SharedPreferences sharedpreferences;
    private BrowserController browserController;

    public void setBaseModel(BaseModel gameModel) {
        this.gameModel = (GameModel) gameModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_info_fragment, container, false);
        location = (TextView) v.findViewById(R.id.location);
        place = (RelativeLayout) v.findViewById(R.id.place);
        place.setEnabled(true);
        title = (TextView) v.findViewById(R.id.textView3);
        about = (TextView) v.findViewById(R.id.about);
        description = (TextView) v.findViewById(R.id.description);
        iw = (ImageView) v.findViewById(R.id.imageView3);
        description2 = (TextView) v.findViewById(R.id.description2);
        second = (RelativeLayout) v.findViewById(R.id.second);
        ImageLoader imageLoader = ImageLoader.getInstance();
        browserController = new BrowserController(getActivity());
        ModelsController modelsController = new ModelsController(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(gameInfoPref, Context.MODE_PRIVATE);
        if(null == gameModel){
            int id = sharedpreferences.getInt(key,-1);
            gameModel = modelsController.getGameModelById(id);
        }
        PlaceModel placeModel = modelsController.getPlaceModelById(gameModel.placeId);
        if(!gameModel.link_facebook.isEmpty()) {
            linkF = (ImageView) v.findViewById(R.id.imageFb);
            imageLoader.displayImage("drawable://" + R.drawable.social_fb, linkF);
            linkF.setOnClickListener(this);
        }
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TerritoryActivity.class);
                intent.putExtra("place_latitude", placeModel.latitude);
                intent.putExtra("place_longitude", placeModel.longitude);
                intent.putExtra("name", gameModel.title);
                GameInfoFragment.this.startActivity(intent);
                place.setEnabled(false);
            }
        });
        int photo_id = getResources().getIdentifier("p" + gameModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + photo_id, iw);
        int marginTop = -12;
        if(PhotoController.isItSmallScreen(getActivity())){
            marginTop = -7;
            iw.getLayoutParams().height = 149;
            iw.requestLayout();
        }
        location.setText(placeModel.name);
        List<GameTimetableModel> timetables = modelsController.getGameTimetableModelsByGameId(gameModel.id);
        description.setText(DateController.convertDate(timetables.get(0)));
        if (timetables.size() != 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.BELOW, R.id.description);
            params.setMargins(0, marginTop, 0, 0);
            second.setLayoutParams(params);
            second.requestLayout();
            description2.setText(DateController.convertDate(timetables.get(1)));
        }
        title.setText(gameModel.title);
        about.setText(gameModel.about);
        setTypefaces();
        return v;
    }

    private void setTypefaces() {
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setFutura(title);
        typefaceController.setFutura(description);
        typefaceController.setFutura(description2);
        typefaceController.setFutura(location);
    }

    @Override
    public void onClick(View v) {
        browserController.openBrowser(gameModel.link_facebook);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, gameModel.id);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        place.setEnabled(true);
    }
}
