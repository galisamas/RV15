package com.itworks.festapp.menu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.itworks.festapp.ComingSoonActivity;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistsActivity;
import com.itworks.festapp.games.GamesActivity;
import com.itworks.festapp.info.InfoActivity;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.stages.StagesActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MenuFragment extends Fragment implements View.OnClickListener {

    Button b1,b2,b3,b4,b5, b6;
    ImageView radistaiLogo, zipLogo, festLogo, logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_fragment, container, false);
        RelativeLayout img = (RelativeLayout) v.findViewById(R.id.menu_background);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage("drawable://" + R.drawable.trp_bg, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                img.setBackgroundDrawable(new BitmapDrawable(loadedImage));
            }
        });

        radistaiLogo = (ImageView) v.findViewById(R.id.radistaiLogo);
        zipLogo = (ImageView) v.findViewById(R.id.ziplogo);
        festLogo = (ImageView) v.findViewById(R.id.festLogo);
        logo = (ImageView) v.findViewById(R.id.logo);
        imageLoader.displayImage("drawable://"+ R.drawable.radistai_logo, radistaiLogo);
        imageLoader.displayImage("drawable://"+ R.drawable.zipfm, zipLogo);
        imageLoader.displayImage("drawable://"+ R.drawable.fest_app, festLogo);
        imageLoader.displayImage("drawable://"+ R.drawable.logo, logo);

        b1 = (Button) v.findViewById(R.id.button);
        b2 = (Button) v.findViewById(R.id.button2);
        b3 = (Button) v.findViewById(R.id.button3);
        b4 = (Button) v.findViewById(R.id.button4);
        b4.setEnabled(true);
        b5 = (Button) v.findViewById(R.id.button5);
        b6 = (Button) v.findViewById(R.id.button6);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = bundle.getInt("id", -1);
            String text = bundle.getString("text");
            if(id!=-1 && text==null){
                Intent intent;
                if(bundle.getBoolean("isItArtist"))
                    intent = new Intent(getActivity(), ArtistsActivity.class);
                else
                    intent = new Intent(getActivity(), GamesActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }else if(text !=null && !text.equals("")){
                setAlertDialog(text);
            }
        }

        if (isItSmallScreen()) {
            MenuBottomFragment menuBottomFragment = (MenuBottomFragment) (getFragmentManager().findFragmentById(R.id.bottomLine));
            ViewGroup.LayoutParams params = menuBottomFragment.getView().getLayoutParams();
            params.height = 0;
            menuBottomFragment.getView().setLayoutParams(params); // TODO reikia apacios maziems ekranams, kazkokios bent
        }
        return v;
    }

    private void setAlertDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.gc();
    }

    @Override
    public void onResume() {
        super.onResume();
        b4.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.button){
            intent = new Intent(getActivity(),ArtistsActivity.class);
        }
        else if (view.getId() == R.id.button2){
            intent = new Intent(getActivity(),StagesActivity.class);
        }
        else if (view.getId() == R.id.button3){
            intent = new Intent(getActivity(),ComingSoonActivity.class); // TODO FoodActivity
        }
        else if (view.getId() == R.id.button4){
            intent = new Intent(getActivity(),TerritoryActivity.class);
            b4.setEnabled(false);
        }
        else if (view.getId() == R.id.button5){
            intent = new Intent(getActivity(),InfoActivity.class);
        }
        else if(view.getId() == R.id.button6){
            intent = new Intent(getActivity(),GamesActivity.class);
        }
        getActivity().startActivity(intent);

    }

    private boolean isItSmallScreen(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return (height < 900 )? true: false;
    }
}