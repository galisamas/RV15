package com.itworks.festapp.menu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistsActivity;
import com.itworks.festapp.food.FoodActivity;
import com.itworks.festapp.games.GamesActivity;
import com.itworks.festapp.helpers.PhotoController;
import com.itworks.festapp.info.InfoActivity;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.stages.StagesActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MenuFragment extends Fragment implements View.OnClickListener {

    Button b1,b2,b3,b4,b5,b6;
    ImageView festLogo, logo;
    Space space1, space2, space3;
    private MenuBottomFragment element;
    private FrameLayout bottomLine;
    private boolean onCreateViewDone = false;

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

        festLogo = (ImageView) v.findViewById(R.id.festLogo);
        logo = (ImageView) v.findViewById(R.id.logo);
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
        element = new MenuBottomFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.bottomLine, element);
        transaction.commit();

        space1 = (Space) v.findViewById(R.id.space1);
        space2 = (Space) v.findViewById(R.id.space2);
        space3 = (Space) v.findViewById(R.id.space3);
        bottomLine = (FrameLayout) v.findViewById(R.id.bottomLine);
        return v;
    }

    public void onWindowFocusChanged(FragmentActivity context) {
        identifySpaceHeight(context);
    }

    private void identifySpaceHeight(FragmentActivity context) {
        int height = calculateSpaceHeight(context);
        space1.getLayoutParams().height = height;
        space2.getLayoutParams().height = height;
        space3.getLayoutParams().height = height;
        space1.requestLayout();
        space2.requestLayout();
        space3.requestLayout();
    }

    private int calculateSpaceHeight(FragmentActivity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int logoH = logo.getHeight();
        int festH = (int) (festLogo.getHeight()*(PhotoController.isItSmallScreen(context)?-1:1.5));
        int buttonsH = b1.getHeight()*2;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) b4.getLayoutParams();
        bottomLine.getLayoutParams().width = b1.getWidth()*3 + lp.leftMargin*2;
        bottomLine.requestLayout();
        int fragmentH = element.onWindowFocusChanged(bottomLine.getLayoutParams().width);
//        Log.d("DYDIS", size.y + " " + logoH + " " + festH + " " + buttonsH + " " + fragmentH + " " + " " + (size.y - logoH - festH - buttonsH - fragmentH)); // TODO log
        return (size.y-logoH+festH-buttonsH-fragmentH)/3;
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
            intent = new Intent(getActivity(),FoodActivity.class);
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
}