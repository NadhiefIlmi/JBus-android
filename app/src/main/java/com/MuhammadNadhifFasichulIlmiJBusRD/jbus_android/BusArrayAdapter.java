package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Bus;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying Bus objects in a ListView.
 */
public class BusArrayAdapter extends ArrayAdapter<Bus> {

    /**
     * Constructor for the BusArrayAdapter.
     *
     * @param context The context in which the adapter is used.
     * @param objects List of Bus objects to be displayed.
     */
    public BusArrayAdapter(Context context, List<Bus> objects) {
        super(context, 0, objects);
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout if the recycled view is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        // Retrieve the bus name TextView from the inflated layout
        TextView busNameTextView = convertView.findViewById(R.id.bus_name);

        // Get the Bus object at the specified position
        Bus bus = getItem(position);

        // Set the text of the bus name TextView with the name of the Bus object
        if (bus != null) {
            busNameTextView.setText(bus.name);
        }

        // Return the modified view
        return convertView;
    }
}
