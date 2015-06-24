package com.itworks.festapp.games;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.ModelsController;
import com.itworks.festapp.helpers.comparators.GameListComparator;
import com.itworks.festapp.models.GameModel;
import com.itworks.festapp.models.GamesListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamesListAdapterFragment extends BaseListFragment {

    private List<GameModel> games;
    private ModelsController modelsController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new AsyncTask<Void, Void, Void>() {
            List<GamesListItem> mItems;
            @Override
            protected Void doInBackground(Void... params) {
                mItems = new ArrayList<>();
                JSONRepository jsonRepository = new JSONRepository(getActivity());
                games = jsonRepository.getGamesFromJSON();
                modelsController = new ModelsController(getActivity());
                Collections.sort(games, new GameListComparator());
                for (GameModel game : games) {
                    int photo_id = activity.getResources().getIdentifier("n" + game.id, "drawable", packageName);
                    mItems.add(new GamesListItem(photo_id, game.title));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setListAdapter(new GamesListAdapter(activity, mItems));
                Bundle bundle = GamesListAdapterFragment.this.getArguments();
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

    private void openInfoById(int position) {
        openInfo(modelsController.getGameModelById(position), new GameInfoFragment());
    }

    private void openInfoByPosition(int position) {
        openInfo(games.get(position), new GameInfoFragment());
    }

}
