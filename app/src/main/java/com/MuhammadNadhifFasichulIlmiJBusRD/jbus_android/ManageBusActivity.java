package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Bus;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.BaseApiService;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for managing buses, allowing users to view and add buses.
 * This class displays a list of buses and provides options to add new buses.
 */
public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView myBusListView;
    ImageView addSched;

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
        setContentView(R.layout.activity_manage_bus);

        // Set the action bar title
        getSupportActionBar().setTitle("Manage Bus");

        // Initialize context and API service
        mContext = this;
        mApiService = UtilsApi.getApiService();
        myBusListView = this.findViewById(R.id.bus_list_view);

        // Load the list of buses
        loadMyBus();
    }

    /**
     * Load the list of buses from the API and populate the ListView.
     */
    protected void loadMyBus() {
        mApiService.getAllBus().enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                MyArrayAdapter adapter = new MyArrayAdapter(mContext, myBusList);
                myBusListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                // Handle failure, if needed
            }
        });
    }

    /**
     * Custom ArrayAdapter for displaying buses in a ListView.
     */
    private class MyArrayAdapter extends ArrayAdapter<Bus> {

        public MyArrayAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;

            // If the recyclable view is null, inflate the custom layout
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_bus_view, parent, false);
            }

            // Get the position of the view from the ArrayAdapter
            Bus currentBus = getItem(position);

            // Assign the desired TextView 1 based on the position of the view
            TextView busName = currentItemView.findViewById(R.id.bus_name);
            busName.setText(currentBus.name);

            // Assign the desired TextView 2 based on the position of the view
            ImageView addSched = currentItemView.findViewById(R.id.manage_schedule);
            addSched.setOnClickListener(v -> {
                // Start ManageBusSchedule activity with the bus ID
                Intent i = new Intent(mContext, ManageBusSchedule.class);
                i.putExtra("busId", currentBus.id);
                mContext.startActivity(i);
            });

            // Return the recyclable view
            return currentItemView;
        }
    }

    /**
     * Create options menu in the action bar.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_bus_menu, menu);
        return true;
    }

    /**
     * Handle menu item selection.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_button) {
            // Start AddBusActivity with the type "addBus"
            Intent intent = new Intent(mContext, AddBusActivity.class);
            intent.putExtra("type", "addBus");
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
