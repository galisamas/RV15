package com.itworks.festapp.artists;

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
import com.activeandroid.query.Select;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.DateHelper;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.helpers.NotificationHelper;
import com.itworks.festapp.helpers.OriginHelper;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.ArtistNotificationModel;
import com.itworks.festapp.models.PlaceModel;
import com.itworks.festapp.models.TimetableModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naglis on 2015-02-05.
 */
public class ArtistInfoFragment extends Fragment implements View.OnClickListener {

    private final String no_link = "No link";
    private final String no_internet = "No internet connection";
    private final String artistInfoPref = "ArtistInfoPref";
    private final String key = "id";
    ImageView photo, linkF, linkY, linkSc, linkSp, flag, alarm;
    TextView about, title, description, location, description2, location2;
    RelativeLayout place, place2, second, background;
    private ArtistModel artistModel;
    private ImageLoader imageLoader;
    private JSONHelper jsonHelper;
    private List<PlaceModel> places;
    private List<TimetableModel> timetables;
    private SharedPreferences sharedpreferences;

    public void setArtistModel(ArtistModel artistModel) {
        this.artistModel = artistModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.artist_info_fragment, container, false);
        about = (TextView) v.findViewById(R.id.about);
        second = (RelativeLayout) v.findViewById(R.id.second);
        background = (RelativeLayout) v.findViewById(R.id.background);
        title = (TextView) v.findViewById(R.id.textView3);
        description = (TextView) v.findViewById(R.id.description);
        place = (RelativeLayout) v.findViewById(R.id.place);
        place.setEnabled(true);
        location = (TextView) v.findViewById(R.id.location);
        description2 = (TextView) v.findViewById(R.id.description2);
        place2 = (RelativeLayout) v.findViewById(R.id.place2);
        place2.setEnabled(true);
        location2 = (TextView) v.findViewById(R.id.location2);

        flag = (ImageView) v.findViewById(R.id.flag);
        alarm = (ImageView) v.findViewById(R.id.alarm);
        photo = (ImageView) v.findViewById(R.id.imageView3);
        linkF = (ImageView) v.findViewById(R.id.imageFb);
        linkY = (ImageView) v.findViewById(R.id.imageYoutube);
        linkSc = (ImageView) v.findViewById(R.id.imageSoudcloud);
        linkSp = (ImageView) v.findViewById(R.id.imageSpotify);
        linkF.setOnClickListener(this);
        linkY.setOnClickListener(this);
        linkSc.setOnClickListener(this);
        linkSp.setOnClickListener(this);
        imageLoader = ImageLoader.getInstance();
        jsonHelper = new JSONHelper(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(artistInfoPref, Context.MODE_PRIVATE);
        if(null == artistModel){
            int id = sharedpreferences.getInt(key,-1);
            artistModel = getArtistModelById(id);
        }
        places = jsonHelper.getPlacesFromJSON();
        paintSocialIcons();
        timetables = getTimetableModels();
        setAlarm();

        about.setText(artistModel.about);
        if(!timetables.isEmpty()) {
            description.setText(DateHelper.convertDateFWE(timetables.get(0).day + "/" + timetables.get(0).start_time));
            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TerritoryActivity.class);
                    intent.putExtra("place_latitude", convertPlaceIdToLat(timetables.get(0).stageId));
                    intent.putExtra("place_longitude", convertPlaceIdToLng(timetables.get(0).stageId));
                    intent.putExtra("name", artistModel.title);
                    intent.putExtra("snippet", description.getText());
                    ArtistInfoFragment.this.startActivity(intent);
                    place.setEnabled(false);
                    place2.setEnabled(false);
                }
            });
            location.setText(convertPlaceIdToTitle(timetables.get(0).stageId));
            if (timetables.size() != 1) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.BELOW, R.id.description);
                params.setMargins(0, -12, 0, 0);
                second.setLayoutParams(params);
                second.requestLayout();
                description2.setText(DateHelper.convertDateFWE(timetables.get(1).day + "/" + timetables.get(1).start_time));
                location2.setText(convertPlaceIdToTitle(timetables.get(1).stageId));
                place2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TerritoryActivity.class);
                        intent.putExtra("place_latitude", convertPlaceIdToLat(timetables.get(1).stageId));
                        intent.putExtra("place_longitude", convertPlaceIdToLng(timetables.get(1).stageId));
                        intent.putExtra("name", convertPlaceIdToTitle(timetables.get(1).stageId));
                        intent.putExtra("snippet", description2.getText());
                        ArtistInfoFragment.this.startActivity(intent);
                        place.setEnabled(false);
                        place2.setEnabled(false);
                    }
                });
            }
        }
        int photo_id = getResources().getIdentifier("b"+artistModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + photo_id, photo);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title.setText(artistModel.title);
        int flag_id = getResources().getIdentifier(OriginHelper.getOriginDrawableTitle(artistModel.origin),
                "drawable", getActivity().getPackageName());
        imageLoader.displayImage("drawable://" + flag_id, flag);
        setTypefaces();
        return v;
    }

    private void setAlarm() {
        ArtistNotificationModel artistNotificationModel = new Select()
                .from(ArtistNotificationModel.class)
                .where("artistId = ?", artistModel.id)
                .executeSingle();
        if(artistNotificationModel.notification){
            imageLoader.displayImage("drawable://" + R.drawable.alarm_on, alarm);
        }
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastText;
                int imageId = R.drawable.alarm_on;
                if(artistNotificationModel.notification){
                    imageId = R.drawable.alarm_off;
                    toastText = getString(R.string.toast_cancel);
                    artistNotificationModel.notification = false;
                    NotificationHelper.cancelAlarm(getActivity(), artistModel.id);
                }else{
                    artistNotificationModel.notification = true;
                    toastText = getString(R.string.toast_start);
                    for(TimetableModel timetable : timetables){
                        String where = convertPlaceIdToWhere(timetable.stageId);
                        NotificationHelper.setAlarm(getActivity(), artistModel.title, artistModel.id, true, where, timetable);
                    }
                }
                Toast.makeText(getActivity().getApplicationContext(), toastText, Toast.LENGTH_LONG).show();

                artistNotificationModel.save();
                imageLoader.displayImage("drawable://" + imageId, alarm);
            }
        });
    }

    private void setTypefaces() {
        Typeface futura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/futura_condensed_medium.ttf");
        Typeface arial = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial_narrow.ttf");
        about.setTypeface(arial);
        title.setTypeface(futura);
        description.setTypeface(futura);
        location.setTypeface(futura);
        description2.setTypeface(futura);
        location2.setTypeface(futura);
    }

    private void paintSocialIcons() {
        if(!artistModel.link_facebook.isEmpty())
            imageLoader.displayImage("drawable://" + R.drawable.social_fb, linkF);
        if(!artistModel.link_youtube.isEmpty())
            imageLoader.displayImage("drawable://" + R.drawable.social_youtube, linkY);
        if(!artistModel.link_soundcloud.isEmpty())
            imageLoader.displayImage("drawable://" + R.drawable.social_soudcloud, linkSc);
        if(!artistModel.link_spotify.isEmpty())
            imageLoader.displayImage("drawable://" + R.drawable.social_spotify, linkSp);

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
    public void onClick(View v) {
        String url = "";
        switch (v.getId()){
            case R.id.imageFb:
                url = artistModel.link_facebook;
                break;
            case R.id.imageYoutube:
                url = artistModel.link_youtube;
                break;
            case R.id.imageSoudcloud:
                url = artistModel.link_soundcloud;
                break;
            case R.id.imageSpotify:
                url = artistModel.link_spotify;
                break;
        }
        openBrowser(url);
    }

    private String convertPlaceIdToWhere(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).where;
            }
        }
        return "";
    }

    private String convertPlaceIdToTitle(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).name;
            }
        }
        return "";
    }

    private Double convertPlaceIdToLat(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).latitude;
            }
        }
        return 0.0;
    }

    private Double convertPlaceIdToLng(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).longitude;
            }
        }
        return 0.0;
    }

    private List<TimetableModel> getTimetableModels(){
        List<TimetableModel> result = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonHelper.getTimetableFromJSON();
        for(int i =0;i<timetableModels.size();i++){
            if(timetableModels.get(i).artistId == artistModel.id){
                result.add(timetableModels.get(i));
            }
        }
        return result;
    }

    private ArtistModel getArtistModelById(int id){
        List<ArtistModel> list = jsonHelper.getArtistsFromJSON();
        for(ArtistModel anArtistModel: list){
            if(anArtistModel.id == id){
                return anArtistModel;
            }
        }
        return new ArtistModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        place.setEnabled(true);
        place2.setEnabled(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, artistModel.id);
        editor.apply();
    }
}