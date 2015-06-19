package com.itworks.festapp.stages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.models.StageListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class StagesListAdapter extends ArrayAdapter<StageListItem> {

    private final Context context;

    public StagesListAdapter(Context context, List<StageListItem> items) {
        super(context, R.layout.stages_list_item, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stages_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivLine = (ImageView) convertView.findViewById(R.id.item_line);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.item_time);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        StageListItem item = getItem(position);
        viewHolder.ivLine.setBackgroundColor(item.colorId);
        ImageLoader imageLoader = ImageLoader.getInstance();
        int id = context.getResources().getIdentifier(item.photoId, "drawable", context.getPackageName());
        imageLoader.displayImage("drawable://" + id, viewHolder.ivIcon);
        viewHolder.tvTime.setText(item.time + ", " + item.place);
        viewHolder.tvTitle.setText(item.name);
        TypefaceController typefaceController = new TypefaceController(context.getAssets());
        typefaceController.setFutura(viewHolder.tvTitle);
        typefaceController.setFutura(viewHolder.tvTime);

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivLine;
        ImageView ivIcon;
        TextView tvTime;
        TextView tvTitle;
    }
}