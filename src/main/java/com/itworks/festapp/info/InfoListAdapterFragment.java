package com.itworks.festapp.info;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.models.InfoListItem;
import com.itworks.festapp.models.PlaceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InfoListAdapterFragment extends ListFragment {

    private final int DRIVE_ME_THERE = 0;
    private final int RULES = 1;
    private final int HISTORY = 2;
    private final int DODONT = 3;
    private final int BRING = 4;
    private final int PARKING = 5;
    private final int TICKETS = 6;
    private final int HOWTOUSE = 7;
    private final int ITWORKS = 8;

    private final String destinationName = "Radistai Village";
    private List<InfoListItem> mItems;
    private PlaceModel place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<PlaceModel> places = jsonRepository.getPlacesFromJSON();
        place = places.get(6);
        mItems = new ArrayList<>();
        String[] values = getResources().getStringArray(R.array.info_list_names);
        for (String value : values) mItems.add(new InfoListItem(value));
        setListAdapter(new InfoListAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(getResources().getDrawable(R.color.list_separator));
        getListView().setDividerHeight(2);
        getListView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String photoId = "photoId", jsonId = "jsonId";
        if(position == DRIVE_ME_THERE){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.kaip_atvykti);
            bundle.putInt(jsonId, DRIVE_ME_THERE);
//            driveMeThere();
//            return;
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
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.bilietai);
            bundle.putInt(jsonId, TICKETS);
        } else if(position == HOWTOUSE){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.kas_per_festapp);
            bundle.putInt(jsonId, HOWTOUSE);
        } else if(position == ITWORKS){
            fragment = new InfoBaseFragment();
            bundle.putInt(photoId, R.drawable.itworks);
            bundle.putInt(jsonId, ITWORKS);
        }
        fragment.setArguments(bundle);
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void driveMeThere(){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", place.latitude,
                place.longitude, destinationName);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(getActivity(), getString(R.string.toast_warning), Toast.LENGTH_LONG).show();
            }
        }
    }

}