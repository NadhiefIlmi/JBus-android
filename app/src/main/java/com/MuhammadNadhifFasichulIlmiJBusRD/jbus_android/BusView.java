package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity to display detailed information about a bus.
 * This class is responsible for showing the detailed view of a specific bus.
 */
public class BusView extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState.
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_view);
    }
}
