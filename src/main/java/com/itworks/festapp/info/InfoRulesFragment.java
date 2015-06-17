package com.itworks.festapp.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.TypefaceController;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class InfoRulesFragment extends Fragment {

    TextView about1, about2, about3, about4, about5, about6, about7, about8, about9, about10, about11, about12, about13,
            about14, about15, about16, about17, about18, about19, about20;
    ImageView photo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_rules_fragment, container, false);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<String> rules = jsonRepository.getRulesFromJSON();
        photo = (ImageView) v.findViewById(R.id.imageView3);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + R.drawable.taisykles, photo);
        about1 = (TextView) v.findViewById(R.id.about1);
        about2 = (TextView) v.findViewById(R.id.about2);
        about3 = (TextView) v.findViewById(R.id.about3);
        about4 = (TextView) v.findViewById(R.id.about4);
        about5 = (TextView) v.findViewById(R.id.about5);
        about6 = (TextView) v.findViewById(R.id.about6);
        about7 = (TextView) v.findViewById(R.id.about7);
        about8 = (TextView) v.findViewById(R.id.about8);
        about9 = (TextView) v.findViewById(R.id.about9);
        about10 = (TextView) v.findViewById(R.id.about10);
        about11 = (TextView) v.findViewById(R.id.about11);
        about12 = (TextView) v.findViewById(R.id.about12);
        about13 = (TextView) v.findViewById(R.id.about13);
        about14 = (TextView) v.findViewById(R.id.about14);
        about15 = (TextView) v.findViewById(R.id.about15);
        about16 = (TextView) v.findViewById(R.id.about16);
        about17 = (TextView) v.findViewById(R.id.about17);
        about18 = (TextView) v.findViewById(R.id.about18);
        about19 = (TextView) v.findViewById(R.id.about19);
        about20 = (TextView) v.findViewById(R.id.about20);

        about1.setText(rules.get(0));
        about4.setText(rules.get(1));
        about6.setText(rules.get(2));
        about8.setText(rules.get(3));
        about10.setText(rules.get(4));
        about12.setText(rules.get(5));
        about14.setText(rules.get(6));
        about16.setText(rules.get(7));
        about18.setText(rules.get(8));
        about20.setText(rules.get(9));
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about1);
        typefaceController.setArial(about4);
        typefaceController.setArial(about6);
        typefaceController.setArial(about8);
        typefaceController.setArial(about10);
        typefaceController.setArial(about12);
        typefaceController.setArial(about14);
        typefaceController.setArial(about16);
        typefaceController.setArial(about18);
        typefaceController.setArial(about20);
        typefaceController.setArialBold(about2);
        typefaceController.setArialBold(about9);
        typefaceController.setArialBold(about11);
        typefaceController.setArialBold(about13);
        typefaceController.setArialBold(about15);
        typefaceController.setArialBold(about17);
        typefaceController.setArialBold(about19);
        typefaceController.setArialItalic(about3);
        typefaceController.setArialItalic(about5);
        typefaceController.setArialItalic(about7);

        return v;
    }
}