package com.itworks.festapp.info;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.itworks.festapp.helpers.JSONRepository;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class InfoBaseFragment extends Fragment implements View.OnClickListener {

    private final String no_link = "No link";
    private final String no_internet = "No internet connection";
    TextView about, link;
    ImageView header;
    private List<String> infoList;

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
        imageLoader.displayImage("drawable://" + photoId, header);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        infoList = jsonRepository.getInfoFromJSON(jsonId);
        about.setText(infoList.get(0));
        if(infoList.size() > 1){
            link.setText(getString(R.string.link_text));
            link.setOnClickListener(this);
        }
        Typeface arial = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial_narrow.ttf");
        about.setTypeface(arial);
        link.setTypeface(arial,Typeface.BOLD);
        return v;
    }

    @Override
    public void onClick(View v) {
        openBrowser(infoList.get(1));
    }

    private void openBrowser(String url){
        if(!url.isEmpty()){
            if(isNetworkAvailable()){
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }else{
                Toast.makeText(getActivity().getApplicationContext(), no_internet, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), no_link, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}