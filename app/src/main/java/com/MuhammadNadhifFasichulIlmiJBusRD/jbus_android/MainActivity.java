package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Bus;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.BaseApiService;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main activity that displays a paginated list of buses.
 * This class handles the paginated display of buses and provides navigation to other activities.
 */
public class MainActivity extends AppCompatActivity {

    private Button[] btns;
    private int currentPage = 0;
    private final int pageSize = 12; // You can experiment with this field
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private BaseApiService apiService;

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
        setContentView(R.layout.activity_main);

        // Connect components with their respective IDs
        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.bus_list);

        // Fetch the list of buses and set up pagination
        listBus();
    }

    /**
     * Called after `onCreate` or after returning from another activity.
     * Fetches the list of buses from the API.
     */
    @Override
    protected void onResume() {
        super.onResume();

        apiService = UtilsApi.getApiService();

        // Make the API request to get all buses
        Call<List<Bus>> call = apiService.getAllBus();
        call.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the list of buses obtained from the API
                    listBus = response.body();
                } else {
                    Toast.makeText(MainActivity.this, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set up listeners for the pagination buttons.
     */
    private void buttonListener() {
        // Listener for the previous and next buttons
        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0 ? currentPage - 1 : 0;
            goToPage(currentPage);
        });
        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages - 1 ? currentPage + 1 : currentPage;
            goToPage(currentPage);
        });
    }

    /**
     * Fetch the list of buses from the API and set up pagination.
     */
    private void listBus() {
        apiService = UtilsApi.getApiService();

        // Make the API request to get all buses
        Call<List<Bus>> call = apiService.getAllBus();
        call.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the list of buses obtained from the API
                    listBus = response.body();
                    listSize = listBus.size();
                    paginationFooter();
                    goToPage(currentPage);
                    buttonListener();
                } else {
                    Toast.makeText(MainActivity.this, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
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
        inflater.inflate(R.menu.action_bar_menu, menu);
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
        int id = item.getItemId();
        if (id == R.id.user_button) {
            // User clicked the "User Button"
            moveActivity(this, AboutMeActivity.class);
            return true;
        }
        if (id == R.id.payment_button) {
            // User clicked the "Payment Button"
            moveActivity(this, PaymentActivity.class);
            return true;
        }
        // Add other conditions for additional menu items if needed
        return super.onOptionsItemSelected(item);
    }

    /**
     * Move to another activity.
     *
     * @param ctx The context from which the activity is launched.
     * @param cls The component class that is to be used for the intent.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Set up pagination buttons dynamically based on the number of pages.
     */
    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0 : 1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1));
            // Change with the color you prefer
            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    /**
     * Handle the display of the selected page.
     *
     * @param index The index of the selected page.
     */
    private void goToPage(int index) {
        for (int i = 0; i < noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    /**
     * Scroll to the selected item.
     *
     * @param item The selected item to scroll to.
     */
    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    /**
     * Display a paginated list of buses.
     *
     * @param listBus The list of buses to display.
     * @param page    The selected page to display.
     */
    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);

        BusArrayAdapter busArrayAdapter = new BusArrayAdapter(this, paginatedList);
        busListView.setAdapter(busArrayAdapter);
    }

    /**
     * Custom ArrayAdapter for displaying buses in a ListView.
     */
    public class BusArrayAdapter extends ArrayAdapter<Bus> {

        public BusArrayAdapter(Context context, List<Bus> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
            }

            TextView busNameTextView = convertView.findViewById(R.id.bus_name);
            Bus bus = getItem(position);
            busNameTextView.setText(bus.name);

            busNameTextView.setOnClickListener(v -> {
                Bus selectedBus = getItem(position);
                if (selectedBus != null) {
                    // Navigate to BusDetailActivity with the selected bus ID
                    Intent intent = new Intent(getContext(), BusDetail.class);
                    intent.putExtra("busId", selectedBus.id); // Assuming Bus class has an 'id' field
                    getContext().startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
