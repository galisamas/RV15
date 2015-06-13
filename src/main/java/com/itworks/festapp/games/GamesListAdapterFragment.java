package com.itworks.festapp.games;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.ModelsController;
import com.itworks.festapp.models.GameModel;
import com.itworks.festapp.models.GamesListItem;

import java.util.ArrayList;
import java.util.List;

public class GamesListAdapterFragment extends BaseListFragment {

    private List<GameModel> games;
    private ModelsController modelsController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<GamesListItem> mItems = new ArrayList<>();
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        games = jsonRepository.getGamesFromJSON();
        modelsController = new ModelsController(getActivity());
        for (GameModel game : games) {
            int photo_id = this.getResources().getIdentifier("n" + game.id, "drawable", packageName);
            mItems.add(new GamesListItem(photo_id, game.title));
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        openInfoByPosition(position);
    }

    private void openInfoById(int position) {
        openInfo(modelsController.getGameModelById(position));
    }

    private void openInfoByPosition(int position) {
        openInfo(games.get(position));
    }

}
