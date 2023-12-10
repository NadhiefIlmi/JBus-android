package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
 * Activity for handling user registration.
 * Users can register by providing a username, email, and password.
 */
public class RegisterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;
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
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Connect UI components to variables
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);

        // Set up click listener for the register button
        registerButton.setOnClickListener(v -> handleRegister());
    }

    /**
     * Handle the user registration process when the register button is clicked.
     */
    protected void handleRegister() {
        // Handling empty fields
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make the API request for user registration
        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                // Handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();

                // If registration is successful, finish this activity (back to login activity)
                if (res.success)
                    finish();

                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
