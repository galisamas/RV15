package com.itworks.festapp.artists;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.helpers.ModelsHelper;
import com.itworks.festapp.helpers.comparators.ArtistListComparator;
import com.itworks.festapp.models.ArtistListItem;
import com.itworks.festapp.models.ArtistModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtistsListAdapterFragment extends BaseListFragment {

    private List<ArtistListItem> mItems;
    private List<ArtistModel> artists;
    private ModelsHelper modelHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();
        modelHelper = new ModelsHelper(getActivity());
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        artists = jsonHelper.getArtistsFromJSON();
        Collections.sort(artists, new ArtistListComparator());
        for (ArtistModel artist : artists) {
            int photo_id = getResources().getIdentifier("m" + artist.id, "drawable", packageName);
            mItems.add(new ArtistListItem(photo_id, artist.title));
        }
        setListAdapter(new ArtistsListAdapter(getActivity(), mItems));
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int i = bundle.getInt("id", -1);
            if(i!=-1){
                openInfoById(i);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        openInfoByPosition(position);
    }

    private void openInfoByPosition(int position) {
        openInfo(artists.get(position));
    }

    private void openInfoById(int id) {
        openInfo(modelHelper.getArtistModelById(id));
    }

}