package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Account;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Payment;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.BaseResponse;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.BaseApiService;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for handling bus payment transactions.
 * Users can view payment details, check balance, and perform payment or cancel booking.
 */
public class PaymentActivity extends AppCompatActivity {
    private BaseApiService apiService;
    private TextView balanceTextView;

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
        setContentView(R.layout.activity_payment);

        // Initialize UI components
        TextView paymentTitleTextView = findViewById(R.id.payment_title);
        TextView busNameTextView = findViewById(R.id.bus_name);
        TextView priceDetailTextView = findViewById(R.id.price_detail);
        TextView departureDateTextView = findViewById(R.id.departure_date);
        TextView selectedSeatTextView = findViewById(R.id.selected_seat);

        Button payButton = findViewById(R.id.pay_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        apiService = UtilsApi.getApiService();

        // Get data from Intent extras
        String busName = getIntent().getStringExtra("busName");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String departureDate = getIntent().getStringExtra("departureDate");
        String selectedSeat = getIntent().getStringExtra("selectedSeat");

        // Set data to UI components
        paymentTitleTextView.setText("Payment Details");
        busNameTextView.setText("Bus Name: " + busName);
        priceDetailTextView.setText("Price: " + price);
        departureDateTextView.setText("Departure Date: " + departureDate);
        selectedSeatTextView.setText("Selected Seat: " + selectedSeat);

        // Set up click listeners for buttons
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the balance is sufficient for payment
                if (isBalanceSufficient(price)) {
                    // If balance is sufficient, perform payment
                    payNow();
                } else {
                    // If balance is not sufficient, show a toast indicating failure
                    Toast.makeText(PaymentActivity.this, "Payment failed. Insufficient balance.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel booking
                cancelBooking();
            }
        });
    }

    /**
     * Check if the user's balance is sufficient for the payment.
     *
     * @param amount The amount to be paid.
     * @return True if the balance is sufficient, false otherwise.
     */
    private boolean isBalanceSufficient(double amount) {
        // Implement your logic to check if the balance is sufficient for the payment
        // For example, you can compare the amount with the user's current balance
        // Return true if the balance is sufficient, otherwise return false
        // For demonstration purposes, let's assume the balance is always sufficient
        return true;
    }

    /**
     * Handle the payment process when the user clicks the "Pay Now" button.
     */
    private void payNow() {

        Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
        // You may also finish the activity or navigate to the next screen
        finish();
    }

    /**
     * Handle the cancellation of the booking when the user clicks the "Cancel" button.
     */
    private void cancelBooking() {
        int paymentId = getIntent().getIntExtra("paymentId", -1);

        if (paymentId != -1) {
            // Make the API call to cancel the booking
            apiService.cancel(paymentId).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (response.isSuccessful()) {
                        BaseResponse<Payment> cancelResponse = response.body();
                        if (cancelResponse != null && cancelResponse.success) {
                            // Handle the successful cancellation
                            Toast.makeText(PaymentActivity.this, "Booking canceled", Toast.LENGTH_SHORT).show();

                            // You may also finish the activity or navigate to the previous screen
                            finish();
                        } else {
                            // Handle the case when the cancellation was not successful
                            Toast.makeText(PaymentActivity.this, cancelResponse.message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle the case when the API call was not successful
                        Toast.makeText(PaymentActivity.this, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                    // Handle the case when the API call failed
                    Toast.makeText(PaymentActivity.this, "Problem with the server", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle the case when paymentId is not found in the intent
            Toast.makeText(PaymentActivity.this, "Payment ID not found", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Booking canceled.", Toast.LENGTH_SHORT).show();
        // You may also finish the activity or navigate to the previous screen
        finish();
    }

}
