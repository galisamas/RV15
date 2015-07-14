package com.itworks.festapp.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.models.FoodListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<FoodListItem> {

    private final Context context;
    private final SharedPreferences sharedpreferences;
    private final String foodAdapterPref = "foodAdapterPref";

    public FoodListAdapter(Context context, List<FoodListItem> items) {
        super(context, R.layout.food_list_item, items);
        this.context = context;
        sharedpreferences = context.getSharedPreferences(foodAdapterPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        for(FoodListItem item :items){
            if(item.photoId != 0)
                editor.putInt(item.name, item.photoId);
        }
        editor.apply();
    }

    @Override
    public boolean isEnabled(int position) {
        return position == 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        FoodListItem item = getItem(position);
        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.forward_arrow);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position != 0 ) {
            viewHolder.arrow.setVisibility(View.GONE);
        }else{
            viewHolder.arrow.setVisibility(View.VISIBLE);
        }

        // update the item view
        ImageLoader imageLoader = ImageLoader.getInstance();
        int photoId = sharedpreferences.getInt(item.name, -1);
        imageLoader.displayImage("drawable://" + photoId ,viewHolder.ivIcon);
        viewHolder.tvTitle.setText(item.name);

        TypefaceController typefaceController = new TypefaceController(context.getAssets());
        typefaceController.setFutura(viewHolder.tvTitle);
        return convertView;
    }

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        ImageView arrow;
    }
}

