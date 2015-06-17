package com.itworks.festapp.info;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.BrowserController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.PhotoController;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

public class InfoDriveMeThereFragment extends Fragment implements View.OnClickListener {

    TextView about, about2, link1, link2, link3, link4, link5, link6, link;
    ImageView header;
    private List<String> infoList;
    private BrowserController browserContoller;
    private final String destinationName = "Radistai Village";
    private PlaceModel place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_drive_me_there_fragment, container, false);
        Bundle bundle = this.getArguments();
        int photoId = bundle.getInt("photoId", -1);
        int jsonId = bundle.getInt("jsonId", -1);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<PlaceModel> places = jsonRepository.getPlacesFromJSON();
        place = places.get(6);
        about = (TextView) v.findViewById(R.id.about);
        about2 = (TextView) v.findViewById(R.id.about2);
        link1 = (TextView) v.findViewById(R.id.link1);
        link2 = (TextView) v.findViewById(R.id.link2);
        link3 = (TextView) v.findViewById(R.id.link3);
        link4 = (TextView) v.findViewById(R.id.link4);
        link5 = (TextView) v.findViewById(R.id.link5);
        link6 = (TextView) v.findViewById(R.id.link6);
        link = (TextView) v.findViewById(R.id.link);
        header = (ImageView) v.findViewById(R.id.imageView3);
        ImageLoader imageLoader = ImageLoader.getInstance();
        browserContoller = new BrowserController(getActivity());
        imageLoader.displayImage("drawable://" + photoId, header);
        if(PhotoController.isItSmallScreen(getActivity())){
            header.getLayoutParams().height = 213;
            header.requestLayout();
        }
        infoList = jsonRepository.getInfoFromJSON(jsonId);

        about.setText(infoList.get(0));
        link.setText(infoList.get(1));
        about2.setText(infoList.get(2));
        link1.setText(infoList.get(3));
        link2.setText(infoList.get(5));
        link3.setText(infoList.get(7));
        link4.setText(infoList.get(9));
        link5.setText(infoList.get(11));
        link6.setText(infoList.get(13));

        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
        link3.setOnClickListener(this);
        link4.setOnClickListener(this);
        link5.setOnClickListener(this);
        link6.setOnClickListener(this);
        link.setOnClickListener(this);

        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setArial(about2);
        typefaceController.setArial(link1);
        typefaceController.setArial(link2);
        typefaceController.setArial(link3);
        typefaceController.setArial(link4);
        typefaceController.setArial(link5);
        typefaceController.setArial(link6);
        typefaceController.setArial(link);

        return v;
    }

    @Override
    public void onClick(View v) {
        int index = 4;
        switch (v.getId()){
            case R.id.link1:
                index = 4;
                break;
            case R.id.link2:
                index = 6;
                break;
            case R.id.link3:
                index = 8;
                break;
            case R.id.link4:
                index = 10;
                break;
            case R.id.link5:
                index = 12;
                break;
            case R.id.link6:
                index = 14;
                break;
            case R.id.link:
                driveMeThere();
                return;

        }
        browserContoller.openBrowser(infoList.get(index));
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