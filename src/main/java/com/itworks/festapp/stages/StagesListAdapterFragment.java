package com.itworks.festapp.stages;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistInfoFragment;
import com.itworks.festapp.helpers.DateController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.comparators.TimetableListComparator;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.StageListItem;
import com.itworks.festapp.models.PlaceModel;
import com.itworks.festapp.models.TimetableModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Naglis on 2015-01-31.
 */
public class StagesListAdapterFragment extends ListFragment {

    private List<StageListItem> mItems;
    private int stageNumber;
    private int dayNumber;
    private List<TimetableModel> timetable;
    private List<ArtistModel> artists;
    private Resources resources;
    private List<PlaceModel> places;
    private ImageLoader imageLoader;

    public void setStage(int stageNumber){
        this.stageNumber = stageNumber;
    }
    public void setDay(int dayNumber){
        this.dayNumber = dayNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        timetable = getTimetable(jsonRepository.getTimetableFromJSON());
        imageLoader = ImageLoader.getInstance();
        artists = jsonRepository.getArtistsFromJSON();
        places = jsonRepository.getPlacesFromJSON();
        resources = getResources();
        for(int i=0; i<timetable.size();i++) {
            ArtistModel artist = getArtistById(timetable.get(i).artistId);
            PlaceModel place = getPlaceById(timetable.get(i).stageId);
            String date = DateController.convertTimeFWD(timetable.get(i).start_time, timetable.get(i).end_time);
            mItems.add(new StageListItem(getColorByDate(timetable.get(i)),
                    resources.getIdentifier("m" + artist.id, "drawable", getActivity().getPackageName()),
                    artist.title, date, place.name));
        }
        setListAdapter(new StagesListAdapter(getActivity(), mItems));
    }

    private PlaceModel getPlaceById(int placeId) { // TODO refactor iskelt
        PlaceModel placeModel = null;
        for(int i=0;i< places.size();i++){
            if(placeId == places.get(i).id){
                placeModel = places.get(i);
                break;
            }
        }
        return placeModel;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(getResources().getDrawable(R.color.list_separator));
        getListView().setDividerHeight(2);
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, false, true);
        getListView().setOnScrollListener(listener);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ArtistInfoFragment fragment = new ArtistInfoFragment();
        fragment.setArtistModel(artists.get(timetable.get(position).artistId));

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private List<TimetableModel> getTimetable(List<TimetableModel> list){
        List<TimetableModel> stageTimetable = new ArrayList<>();
        for (TimetableModel aList : list) {
            if (aList.stageId == stageNumber && aList.day == dayNumber) {
                stageTimetable.add(aList);
            }
        }
        Collections.sort(stageTimetable, new TimetableListComparator());
        return stageTimetable;
    }

    private ArtistModel getArtistById(int id){
        ArtistModel artist = null;
        for(int i=0;i<artists.size();i++){
            if(id == artists.get(i).id){
                artist = artists.get(i);
                break;
            }
        }
        return artist;
    }

    private int getColorByDate(TimetableModel timetable){
        if(DateController.calculateIsItNow(dayNumber, timetable.start_time, timetable.end_time))
            return resources.getColor(R.color.now_stage_indicator);
        return resources.getColor(R.color.basic_stage_indicator);
    }


}