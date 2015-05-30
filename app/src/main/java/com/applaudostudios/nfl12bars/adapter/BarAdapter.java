package com.applaudostudios.nfl12bars.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.applaudostudios.nfl12bars.R;
import com.applaudostudios.nfl12bars.models.BarVenue;

import java.util.ArrayList;

/**
 * Created by RafaelCastro on 28/5/15.
 */
public class BarAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<BarVenue> items;
    BarVenue bar;

    String TAG="BarAdapter";
    public BarAdapter(Activity activity, ArrayList<BarVenue> items) {
        super();
        this.activity = activity;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Typeface tfBarName =
                Typeface.createFromAsset(activity.getAssets(),"fonts/gt-walsheim-medium-web.ttf");
        Typeface tfBarAddress =
                Typeface.createFromAsset(activity.getAssets(),"fonts/gt-walsheim-light-web.ttf");
        bar = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_bar, null);
            holder = new ViewHolder();
            holder.mTxtBar = (TextView) convertView.findViewById(R.id.txtBarName);
            holder.mTxtAddress = (TextView) convertView.findViewById(R.id.txtBarAddress);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mTxtBar.setText(bar.getName());
        holder.mTxtBar.setTypeface(tfBarName);
        holder.mTxtAddress.setText(bar.getAddress());
        holder.mTxtAddress.setTypeface(tfBarAddress);

        return convertView;
    }




    static class ViewHolder {
        TextView mTxtBar,mTxtAddress;
    }
}
