package com.itworks.festapp.games;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.models.GamesListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class GamesListAdapter extends ArrayAdapter<GamesListItem> {

    private final Context context;

    public GamesListAdapter(Context context, List<GamesListItem> items) {
        super(context, R.layout.games_list_item, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.games_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        GamesListItem item = getItem(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + item.photoId ,viewHolder.ivIcon);
        viewHolder.tvTitle.setText(item.name);
        Typeface futura = Typeface.createFromAsset(context.getAssets(), "fonts/futura_condensed_medium.ttf");
        viewHolder.tvTitle.setTypeface(futura);

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
    }
}
