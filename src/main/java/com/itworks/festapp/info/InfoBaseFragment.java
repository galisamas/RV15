package com.itworks.festapp.info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class InfoBaseFragment extends Fragment implements View.OnClickListener {

    TextView about, link;
    ImageView header;
    private List<String> infoList;
    private BrowserController browserContoller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_base_fragment, container, false);
        Bundle bundle = this.getArguments();
        int photoId = bundle.getInt("photoId", -1);
        int jsonId = bundle.getInt("jsonId", -1);
        about = (TextView) v.findViewById(R.id.about);
        link = (TextView) v.findViewById(R.id.link);
        header = (ImageView) v.findViewById(R.id.imageView3);
        ImageLoader imageLoader = ImageLoader.getInstance();
        browserContoller = new BrowserController(getActivity());
        imageLoader.displayImage("drawable://" + photoId, header);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        infoList = jsonRepository.getInfoFromJSON(jsonId);
        about.setText(infoList.get(0));
        if(infoList.size() > 2){
            link.setText(infoList.get(1));
            link.setOnClickListener(this);
        }
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setArialBold(link);
        return v;
    }

    @Override
    public void onClick(View v) {
        browserContoller.openBrowser(infoList.get(2));
    }

}