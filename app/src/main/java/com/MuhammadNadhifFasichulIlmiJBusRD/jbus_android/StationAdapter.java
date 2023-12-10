package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Station;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying a list of stations.
 * This adapter is designed to work with Spinner or AutoCompleteTextView.
 */
public class StationAdapter extends ArrayAdapter<Station> {

    /**
     * Constructs a new StationAdapter.
     *
     * @param context  The current context.
     * @param stations The list of stations to be displayed.
     */
    public StationAdapter(Context context, List<Station> stations) {
        super(context, android.R.layout.simple_list_item_1, stations);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        // Set the text of the TextView to the station name
        textView.setText(getItem(position).stationName);
        return textView;
    }

    /**
     * Get a View that displays the data in a drop-down popup window.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        // Set the text of the TextView to the station name
        textView.setText(getItem(position).stationName);
        return textView;
    }
}
