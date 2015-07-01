package com.itworks.festapp.artists;

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
import android.widget.Toast;
import com.activeandroid.query.Select;
import com.itworks.festapp.BaseFragment;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.*;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.ArtistNotificationModel;
import com.itworks.festapp.models.TimetableModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ArtistInfoFragment extends BaseFragment implements View.OnClickListener {

    private final String artistInfoPref = "ArtistInfoPref";
    private final String key = "id";
    private final String drawableString = "drawable://";
    ImageView photo, linkF, linkY, linkSc, linkSp, flag, alarm;
    TextView about, title, description, location, description2, location2;
    RelativeLayout place, place2, second, background;
    private ArtistModel artistModel;
    private ImageLoader imageLoader;
    private List<TimetableModel> timetables;
    private SharedPreferences sharedpreferences;
    private BrowserController browserController;
    private ModelsController modelsController;

//    public void setBaseModel(BaseModel artistModel) {
//        this.artistModel = (ArtistModel) artistModel;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.artist_info_fragment, container, false);
        artistModel = (ArtistModel) baseModel;
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
        browserController = new BrowserController(getActivity());
        modelsController = new ModelsController(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(artistInfoPref, Context.MODE_PRIVATE);
        if(null == artistModel){
            int id = sharedpreferences.getInt(key,-1);
            artistModel = modelsController.getArtistModelById(id);
        }
        paintSocialIcons();
        timetables = modelsController.getTimetableModelsByArtistId(artistModel.id);
        setAlarm();
        int photo_id = getResources().getIdentifier("b"+artistModel.id, "drawable", getActivity().getPackageName());
        imageLoader.displayImage(drawableString + photo_id, photo);
        int marginTop = -12;
        if(PhotoController.isItSmallScreen(getActivity())){
            marginTop = -7;
        }
        about.setText(artistModel.about);
        if(!timetables.isEmpty()) {
            description.setText(DateController.convertDateFWE(timetables.get(0).day + "/" + timetables.get(0).start_time));
            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTerritoryActivity(description.getText(), timetables.get(0).stageId);
                }
            });
            location.setText(modelsController.convertPlaceIdToTitle(timetables.get(0).stageId));
            if (timetables.size() != 1) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.BELOW, R.id.description);

                params.setMargins(0, marginTop, 0, 0);
                second.setLayoutParams(params);
                second.requestLayout();
                description2.setText(DateController.convertDateFWE(timetables.get(1).day + "/" + timetables.get(1).start_time));
                location2.setText(modelsController.convertPlaceIdToTitle(timetables.get(1).stageId));
                place2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTerritoryActivity(description2.getText(), timetables.get(1).stageId);
                    }
                });
            }
        }

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        title.setText(artistModel.title);
        String flagName = OriginRepository.getOriginDrawableTitle(artistModel.origin);
        int flag_id = getResources().getIdentifier(flagName,
                "drawable", getActivity().getPackageName());
        imageLoader.displayImage(drawableString + flag_id, flag);
        setTypefaces();
        return v;
    }

    private void openTerritoryActivity(CharSequence snippet, int stageId) {
        Intent intent = new Intent(getActivity(), TerritoryActivity.class);
        intent.putExtra("place_latitude", modelsController.convertPlaceIdToLat(stageId));
        intent.putExtra("place_longitude", modelsController.convertPlaceIdToLng(stageId));
        intent.putExtra("name", artistModel.title);
        intent.putExtra("snippet", snippet);
        ArtistInfoFragment.this.startActivity(intent);
        place.setEnabled(false);
        place2.setEnabled(false);
    }

    private void setAlarm() {
        ArtistNotificationModel artistNotificationModel = new Select()
                .from(ArtistNotificationModel.class)
                .where("artistId = ?", artistModel.id)
                .executeSingle();
        if(artistNotificationModel.notification){
            imageLoader.displayImage(drawableString + R.drawable.alarm_on, alarm);
        }
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastText;
                int imageId = R.drawable.alarm_on;
                if (artistNotificationModel.notification) {
                    imageId = R.drawable.alarm_off;
                    toastText = getString(R.string.toast_cancel);
                    artistNotificationModel.notification = false;
                    NotificationController.cancelAlarm(getActivity(), artistModel.id);
                } else {
                    artistNotificationModel.notification = true;
                    toastText = getString(R.string.toast_start);
                    for (TimetableModel timetable : timetables) {
                        String where = modelsController.convertPlaceIdToWhere(timetable.stageId);
                        NotificationController.setAlarm(getActivity(), artistModel.title, artistModel.id, true, where, timetable);
                    }
                }
                Toast.makeText(getActivity().getApplicationContext(), toastText, Toast.LENGTH_LONG).show();

                artistNotificationModel.save();
                imageLoader.displayImage(drawableString + imageId, alarm);
            }
        });
    }

    private void setTypefaces() {
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setFutura(title);
        typefaceController.setFutura(description);
        typefaceController.setFutura(location);
        typefaceController.setFutura(description2);
        typefaceController.setFutura(location2);
    }

    private void paintSocialIcons() {
        paintSocialIcon(artistModel.link_facebook, R.drawable.social_fb, linkF);
        paintSocialIcon(artistModel.link_youtube, R.drawable.social_youtube, linkY);
        paintSocialIcon(artistModel.link_soundcloud, R.drawable.social_soudcloud, linkSc);
        paintSocialIcon(artistModel.link_spotify, R.drawable.social_spotify, linkSp);
    }

    private void paintSocialIcon(String link, int photoId, ImageView view){
        if(link != null && !link.isEmpty())
            imageLoader.displayImage(drawableString + photoId, view);
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
        browserController.openBrowser(url);
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