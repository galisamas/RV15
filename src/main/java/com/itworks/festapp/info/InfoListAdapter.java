package com.itworks.festapp.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.models.InfoListItem;

import java.util.List;

public class InfoListAdapter extends ArrayAdapter<InfoListItem> {

    private final Context context;

    public InfoListAdapter(Context context, List<InfoListItem> items) {
        super(context, R.layout.games_list_item, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.info_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // update the item view
        InfoListItem item = getItem(position);
        viewHolder.tvTitle.setText(item.name);
        TypefaceController typefaceController = new TypefaceController(context.getAssets());
        typefaceController.setFutura(viewHolder.tvTitle);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
    }
}
