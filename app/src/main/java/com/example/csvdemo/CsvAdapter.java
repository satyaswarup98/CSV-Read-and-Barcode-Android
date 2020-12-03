package com.example.csvdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CsvAdapter  extends ArrayAdapter<DataSample> {
    public CsvAdapter(Context context, List<DataSample> samples) {
        super(context, 0, samples);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        DataSample currentData = getItem(position);

        TextView unv = (TextView) listItemView.findViewById(R.id.unv);
        unv.setText(currentData.getUnv());
//
//        TextView hi = (TextView) listItemView.findViewById(R.id.hi);
//        hi.setText(""+currentData.getHi());
//
//        TextView ci = (TextView) listItemView.findViewById(R.id.ci);
//        ci.setText(""+currentData.getCi());

        return listItemView;
    }
}
