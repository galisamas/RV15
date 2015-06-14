package com.itworks.festapp.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.BrowserController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.TypefaceController;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class InfoTicketsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    TextView about, link1, link2, link3, link4, link5, link6, link7, link8, link9;
    ImageView header;
    private List<String> infoList;
    private BrowserController browserContoller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.info_tickets_fragment, container, false);
        Bundle bundle = this.getArguments();
        int photoId = bundle.getInt("photoId", -1);
        int jsonId = bundle.getInt("jsonId", -1);
        JSONRepository jsonRepository = new JSONRepository(getActivity());

        about = (TextView) v.findViewById(R.id.about);
        link1 = (TextView) v.findViewById(R.id.link1);
        link2 = (TextView) v.findViewById(R.id.link2);
        link3 = (TextView) v.findViewById(R.id.link3);
        link4 = (TextView) v.findViewById(R.id.link4);
        link5 = (TextView) v.findViewById(R.id.link5);
        link6 = (TextView) v.findViewById(R.id.link6);
        link7 = (TextView) v.findViewById(R.id.link7);
        link8 = (TextView) v.findViewById(R.id.link8);
        link9 = (TextView) v.findViewById(R.id.link9);
        header = (ImageView) v.findViewById(R.id.imageView3);
        browserContoller = new BrowserController(getActivity());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + photoId, header);
        infoList = jsonRepository.getInfoFromJSON(jsonId);
        about.setText(infoList.get(0));
        link1.setText(infoList.get(1));
        link2.setText(infoList.get(2));
        link3.setText(infoList.get(4));
        link4.setText(infoList.get(6));
        link5.setText(infoList.get(8));
        link6.setText(infoList.get(10));
        link7.setText(infoList.get(12));
        link8.setText(infoList.get(14));
        link9.setText(infoList.get(16));

        link2.setOnClickListener(this);
        link3.setOnClickListener(this);
        link4.setOnClickListener(this);
        link5.setOnClickListener(this);
        link6.setOnClickListener(this);
        link7.setOnClickListener(this);
        link8.setOnClickListener(this);
        link9.setOnClickListener(this);

        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setArialBold(link1);
        typefaceController.setArial(link2);
        typefaceController.setArial(link3);
        typefaceController.setArial(link4);
        typefaceController.setArial(link5);
        typefaceController.setArial(link6);
        typefaceController.setArial(link7);
        typefaceController.setArial(link8);
        typefaceController.setArial(link9);

        return v;
    }

    @Override
    public void onClick(View v) {
        int index = 3;
        switch (v.getId()){
            case R.id.link2:
                index = 3;
                break;
            case R.id.link3:
                index = 5;
                break;
            case R.id.link4:
                index = 7;
                break;
            case R.id.link5:
                index = 9;
                break;
            case R.id.link6:
                index = 11;
                break;
            case R.id.link7:
                index = 13;
                break;
            case R.id.link8:
                index = 15;
                break;
            case R.id.link9:
                index = 17;
                break;
        }
        browserContoller.openBrowser(infoList.get(index));
    }
}