/*
 * File: AboutMeActivity.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents the About Me activity, displaying user account information
 *              and providing functionality for top-up and company registration.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Account;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.BaseResponse;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.BaseApiService;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The AboutMeActivity class represents the About Me activity, displaying user account information
 * and providing functionality for top-up and company registration.
 */
public class AboutMeActivity extends AppCompatActivity {

    private Button topupButton = null;
    private Button renterButton = null;
    private TextView renterStatus = null;
    private Button renterButton2 = null;
    private TextView renterStatus2 = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        if (LoginActivity.loggedAccount == null) {
            finish();
            Toast.makeText(this, "Anda belum login", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize components
        TextView usernameTextView = findViewById(R.id.bus);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);

        // Set the account data
        Account loggedAccount = LoginActivity.loggedAccount;
        usernameTextView.setText(loggedAccount.name);
        emailTextView.setText(loggedAccount.email);
        balanceTextView.setText(String.valueOf(loggedAccount.balance));

        // Update the initial letter in the circle with the first letter of the name
        TextView initialTextView = findViewById(R.id.initial);
        if (loggedAccount.name.length() > 0) {
            initialTextView.setText(String.valueOf(loggedAccount.name.charAt(0)).toUpperCase());
        }

        topupButton = findViewById(R.id.topup);
        topupButton.setOnClickListener(v -> handleTopup(v));

        renterButton = findViewById(R.id.renter_button);
        renterStatus = findViewById(R.id.renter_status);
        renterButton2 = findViewById(R.id.renter_button2);
        renterStatus2 = findViewById(R.id.renter_status2);

        if (LoginActivity.loggedAccount.company != null) {
            renterButton2.setVisibility(View.VISIBLE);
            renterStatus2.setVisibility(View.VISIBLE);
            renterButton.setVisibility(View.GONE);
            renterStatus.setVisibility(View.GONE);
        } else {
            renterButton2.setVisibility(View.GONE);
            renterStatus2.setVisibility(View.GONE);
            renterButton.setVisibility(View.VISIBLE);
            renterStatus.setVisibility(View.VISIBLE);
        }

        renterButton.setOnClickListener(v -> {
            moveActivity(this, RegisterRenterActivity.class);
            viewToast(this, "Register company anda");
        });
        renterButton2.setOnClickListener(v -> {
            moveActivity(this, ManageBusActivity.class);
            viewToast(this, "Manage bus anda");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginActivity.loggedAccount.company != null) {
            renterButton2.setVisibility(View.VISIBLE);
            renterStatus2.setVisibility(View.VISIBLE);
            renterButton.setVisibility(View.GONE);
            renterStatus.setVisibility(View.GONE);
        } else {
            renterButton2.setVisibility(View.GONE);
            renterStatus2.setVisibility(View.GONE);
            renterButton.setVisibility(View.VISIBLE);
            renterStatus.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Moves to another activity.
     *
     * @param ctx The current context.
     * @param cls The target activity class.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a short toast message.
     *
     * @param ctx     The current context.
     * @param message The message to be displayed.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the top-up functionality.
     *
     * @param view The current view.
     */
    public void handleTopup(View view) {
        // Get the amount from the EditText
        EditText amountEditText = findViewById(R.id.topup_amount);
        String amountStr = amountEditText.getText().toString();

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        if (amount <= 0) {
            Toast.makeText(this, "Topup amount must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the balance in the Account object
        Account loggedAccount = LoginActivity.loggedAccount;

        double newBalance = loggedAccount.balance + amount;

        // Do the topup request
        BaseApiService mApiService = UtilsApi.getApiService();

        // Get the logged in user ID
        int id = loggedAccount.id;

        mApiService.topUp(id, amount).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AboutMeActivity.this, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Double> res = response.body();

                if (res.success) {
                    // Update the balance in the TextView
                    TextView balanceTextView = findViewById(R.id.balance);
                    loggedAccount.balance = newBalance;
                    balanceTextView.setText(String.valueOf(loggedAccount.balance));

                    Toast.makeText(AboutMeActivity.this, "Topup berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AboutMeActivity.this, res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(AboutMeActivity.this, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
