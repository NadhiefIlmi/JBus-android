package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Bus;

import java.util.List;

/**
 * Custom ArrayAdapter for displaying a list of buses.
 */
public class BusListAdapter extends ArrayAdapter<Bus> {
    private Context mContext;

    /**
     * Constructor for the BusListAdapter.
     *
     * @param context The context.
     * @param buses   The list of buses to display.
     */
    public BusListAdapter(Context context, List<Bus> buses) {
        super(context, 0, buses);
        mContext = context;
    }

    /**
     * Get a view that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // If the view is null, inflate it using the custom layout for bus items
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_bus_item, parent, false);
        }

        // Get the Bus object at the specified position
        Bus bus = getItem(position);

        // Set the bus name in the TextView
        TextView busNameTextView = convertView.findViewById(R.id.bus_name);
        busNameTextView.setText(bus.name);

        // Set a click listener for the calendar icon to view the bus schedule
        ImageView calendarIconImageView = convertView.findViewById(R.id.calendar_icon);
        calendarIconImageView.setTag(position); // Set a tag to identify the position
        calendarIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Bus bus = getItem(position);

                // Start the BusScheduleActivity with the bus ID
                Intent intent = new Intent(mContext, BusScheduleActivity.class);
                intent.putExtra("busId", bus.id);

                // Use the context (mContext) to start the activity
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
