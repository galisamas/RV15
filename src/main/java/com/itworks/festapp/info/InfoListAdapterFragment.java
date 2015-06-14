package com.itworks.festapp.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import com.itworks.festapp.BaseListFragment;
import com.itworks.festapp.R;
import com.itworks.festapp.models.InfoListItem;

import java.util.ArrayList;
import java.util.List;

public class InfoListAdapterFragment extends BaseListFragment {

    private final int DRIVE_ME_THERE = 0;
    private final int RULES = 1;
    private final int HISTORY = 2;
    private final int DODONT = 3;
    private final int BRING = 4;
    private final int PARKING = 5;
    private final int TICKETS = 6;
    private final int HOWTOUSE = 7;
    private final int ITWORKS = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<InfoListItem> mItems = new ArrayList<>();
        String[] values = getResources().getStringArray(R.array.info_list_names);
        for (String value : values) mItems.add(new InfoListItem(value));
        setListAdapter(new InfoListAdapter(getActivity(), mItems));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String photoId = "photoId", jsonId = "jsonId";
        if(position == DRIVE_ME_THERE){
            fragment = new InfoDriveMeThereFragment();
            bundle.putInt(photoId, R.drawable.kaip_atvykti);
            bundle.putInt(jsonId, DRIVE_ME_THERE);
        } else if(position == RULES){
            fragment = new InfoRulesFragment();
        } else if(position == HISTORY){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.istorija);
            bundle.putInt(jsonId, HISTORY);
        } else if(position == DODONT){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.daryti_nedaryti);
            bundle.putInt(jsonId, DODONT);
        } else if(position == BRING){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.ka_pasiimti);
            bundle.putInt(jsonId, BRING);
        } else if(position == PARKING){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.parkavimas);
            bundle.putInt(jsonId, PARKING);
        } else if(position == TICKETS){
            fragment = new InfoTicketsFragment();
            bundle.putInt(photoId, R.drawable.bilietai);
            bundle.putInt(jsonId, TICKETS);
        } else if(position == HOWTOUSE){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.kas_per_festapp);
            bundle.putInt(jsonId, HOWTOUSE);
        } else if(position == ITWORKS){
            fragment = new InfoItWorksFragment();
            bundle.putInt(photoId, R.drawable.itworks);
            bundle.putInt(jsonId, ITWORKS);
        }
        fragment.setArguments(bundle);
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}