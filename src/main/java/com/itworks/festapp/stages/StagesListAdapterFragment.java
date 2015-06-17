package com.itworks.festapp.stages;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistInfoFragment;
import com.itworks.festapp.helpers.DateController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.ModelsController;
import com.itworks.festapp.models.*;

import java.util.ArrayList;
import java.util.List;

public class StagesListAdapterFragment extends BaseListFragment {

    private int stageNumber;
    private int dayNumber;
    private List<BaseTimetable> timetable;
    private List<ArtistModel> artists;
    private Resources resources;

    public void setStage(int stageNumber){
        this.stageNumber = stageNumber;
    }
    public void setDay(int dayNumber){
        this.dayNumber = dayNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<StageListItem> mItems = new ArrayList<>();
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        ModelsController modelsController = new ModelsController(getActivity());
        timetable = modelsController.getTimetablesByStageIdAndByDay(dayNumber, stageNumber);
        artists = jsonRepository.getArtistsFromJSON();
        resources = getResources();
        for (BaseTimetable aTimetable : timetable) {
            ArtistModel artist = modelsController.getArtistModelById(aTimetable.artistId);
            PlaceModel place = modelsController.getPlaceModelById(aTimetable.stageId);
            String date = DateController.convertTimeFWD(aTimetable.start_time, aTimetable.end_time);
            mItems.add(new StageListItem(getColorByDate(aTimetable),
                    resources.getIdentifier("m" + artist.id, "drawable", getActivity().getPackageName()),
                    artist.title, date, place.name));
        }
        setListAdapter(new StagesListAdapter(getActivity(), mItems));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if()
        openInfo(artists.get(timetable.get(position).artistId), new ArtistInfoFragment());
    }

    private int getColorByDate(BaseTimetable timetable){
        if(DateController.calculateIsItNow(dayNumber, timetable.start_time, timetable.end_time))
            return resources.getColor(R.color.now_stage_indicator);
        return resources.getColor(R.color.basic_stage_indicator);
    }


}