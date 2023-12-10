package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Account;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.BaseResponse;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Renter;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.BaseApiService;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for handling the registration of a renter.
 * Renter registration involves providing company information such as company name, address, and phone number.
 */
public class RegisterRenterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText companyName, address, phoneNumber;
    private Button registerButton = null;

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
        setContentView(R.layout.activity_register_renter);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Connect UI components to variables
        companyName = findViewById(R.id.bus);
        address = findViewById(R.id.capacity);
        phoneNumber = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);

        // Set up click listener for the register button
        registerButton.setOnClickListener(v -> handleRegister());
    }

    /**
     * Handle the renter registration process when the register button is clicked.
     */
    protected void handleRegister() {
        // Handling empty fields
        String companyS = companyName.getText().toString();
        String addressS = address.getText().toString();
        String phoneS = phoneNumber.getText().toString();
        if (companyS.isEmpty() || addressS.isEmpty() || phoneS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the logged account information
        Account loggedAccount = LoginActivity.loggedAccount;
        int id = loggedAccount.id;

        // Make the API request for renter registration
        mApiService.registerRenter(id, companyS, addressS, phoneS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                // Handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Renter> res = response.body();

                // If registration is successful, finish this activity (back to login activity)
                if (res.success) {
                    // Update the logged account with renter information
                    LoginActivity.loggedAccount.company = res.payload;
                    finish();
                } else {
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
