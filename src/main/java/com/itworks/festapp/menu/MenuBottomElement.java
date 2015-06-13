package com.itworks.festapp.menu;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistsActivity;
import com.itworks.festapp.helpers.DateController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.TimetableModel;

import java.util.List;

public class MenuBottomElement extends Fragment {

    private TimetableModel timetableModel;
    private ArtistModel artistModel;
    ImageView photo;
    TextView title, time;
    private RelativeLayout bg;

    public void setTimetableModel(TimetableModel timetableModel){
        this.timetableModel = timetableModel;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_bottom_item, container, false);
        bg = (RelativeLayout) v.findViewById(R.id.bottom_element_relative);
        photo = (ImageView) v.findViewById(R.id.item_image);
        title = (TextView) v.findViewById(R.id.item_title);
        time = (TextView) v.findViewById(R.id.item_time);
        setInfo();
        return v;
    }

    public void setInfo() {
        artistModel = new ArtistModel();
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<ArtistModel> artists = jsonRepository.getArtistsFromJSON();

        if(timetableModel != null && !timetableModel.isItEmpty) {
            bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ArtistsActivity.class);
                    intent.putExtra("id", artistModel.id);
                    startActivity(intent);
                }
            });
            for (ArtistModel artist : artists) {
                if (artist.id == timetableModel.artistId) {
                    artistModel = artist;
                }
            }
            int photo_id = getResources().getIdentifier("m" + artistModel.id, "drawable", getActivity().getPackageName());
            photo.setImageResource(photo_id);
            title.setText(artistModel.title);
            time.setText(DateController.convertTimeFWD(timetableModel.start_time, timetableModel.end_time));
        }
        setTypefaces();
    }

    private void setTypefaces() {
        Typeface futura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/futura_condensed_medium.ttf");
        title.setTypeface(futura);
        time.setTypeface(futura);
    }
}