package com.itworks.festapp.artists;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.comparators.ArtistListComparator;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.models.ArtistListItem;
import com.itworks.festapp.models.ArtistModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Naglis on 2015-01-31.
 */
public class ArtistsListAdapterFragment extends ListFragment {

    private String packageName;

    private List<ArtistListItem> mItems;
    private List<ArtistModel> artists;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        artists = jsonHelper.getArtistsFromJSON();
        Collections.sort(artists, new ArtistListComparator());
        for(int i=0; i<artists.size();i++){
            int photo_id = getResources().getIdentifier("m"+artists.get(i).id, "drawable", packageName);
            mItems.add(new ArtistListItem(photo_id, artists.get(i).title));
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(getResources().getDrawable(R.color.list_separator));
        getListView().setDividerHeight(2);
        getListView().setFastScrollEnabled(true);
        ImageLoader imageLoader = ImageLoader.getInstance();
        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
        getListView().setOnScrollListener(listener);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        openInfoByPosition(position);
    }

    private void openInfoByPosition(int position) {
        openInfo(artists.get(position));
    }

    private void openInfoById(int position) {
        openInfo(getArtistById(position));
    }

    private void openInfo(ArtistModel artistModel){ // TODO refactor iskelt
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ArtistInfoFragment fragment = new ArtistInfoFragment();
        fragment.setArtistModel(artistModel);

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private ArtistModel getArtistById(int id){ // TODO refactor iskelt
        ArtistModel artist = null;
        for(int i=0;i<artists.size();i++){
            if(id == artists.get(i).id){
                artist = artists.get(i);
                break;
            }
        }
        return artist;
    }
}