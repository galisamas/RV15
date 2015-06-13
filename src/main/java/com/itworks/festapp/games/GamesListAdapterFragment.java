package com.itworks.festapp.games;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.models.GameModel;
import com.itworks.festapp.models.GamesListItem;
import com.itworks.festapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class GamesListAdapterFragment extends ListFragment {

    private String packageName;
    private List<GamesListItem> mItems;
    private List<GameModel> games;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<>();
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        games = jsonHelper.getGamesFromJSON();
        for(int i=0; i<games.size();i++) {
            int photo_id = this.getResources().getIdentifier("n" + games.get(i).id, "drawable", packageName);
            mItems.add(new GamesListItem(photo_id, games.get(i).title));
        }
        setListAdapter(new GamesListAdapter(getActivity(), mItems));
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
        getListView().setBackgroundColor(Color.WHITE);
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

    private void openInfoById(int position) {
        openInfoFragment(getGameById(position));
    }

    private void openInfoByPosition(int position) {
        openInfoFragment(games.get(position));
    }


    private void openInfoFragment(GameModel gameModel) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        GameInfoFragment fragment = new GameInfoFragment();
        fragment.setGameModel(gameModel);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private GameModel getGameById(int id){
        GameModel game = null;
        for(int i=0;i<games.size();i++){
            if(id == games.get(i).id){
                game = games.get(i);
                break;
            }
        }
        return game;
    }

}
