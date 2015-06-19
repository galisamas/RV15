package com.itworks.festapp.artists;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.ModelsController;
import com.itworks.festapp.helpers.comparators.ArtistListComparator;
import com.itworks.festapp.models.ArtistListItem;
import com.itworks.festapp.models.ArtistModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtistsListAdapterFragment extends BaseListFragment {

    private List<ArtistModel> artists;
    private ModelsController modelHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new AsyncTask<Void, Void, Void>() {
            List<ArtistListItem> mItems;

            @Override
            protected Void doInBackground(Void... params) {
                mItems = new ArrayList<>();
                modelHelper = new ModelsController(activity);
                JSONRepository jsonRepository = new JSONRepository(getActivity());
                artists = jsonRepository.getArtistsFromJSON();
                Collections.sort(artists, new ArtistListComparator());
                for (ArtistModel artist : artists) {
                    int photo_id = getResources().getIdentifier("m" + artist.id, "drawable", packageName);
                    mItems.add(new ArtistListItem(photo_id, artist.title));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setListAdapter(new ArtistsListAdapter(activity, mItems));
                Bundle bundle = ArtistsListAdapterFragment.this.getArguments();
                if (bundle != null) {
                    int i = bundle.getInt("id", -1);
                    if(i!=-1){
                        openInfoById(i);
                    }
                }
            }
        }.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        openInfoByPosition(position);
    }

    private void openInfoByPosition(int position) {
        openInfo(artists.get(position), new ArtistInfoFragment());
    }

    private void openInfoById(int id) {
        openInfo(modelHelper.getArtistModelById(id), new ArtistInfoFragment());
    }

}