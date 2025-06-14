package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
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
 * Activity for user login.
 * This class is responsible for handling user login functionality.
 */
public class LoginActivity extends AppCompatActivity {

    // Define the variables
    private Button registerNow = null;
    private Button loginButton = null;
    public static Account loggedAccount;

    private BaseApiService mApiService;
    private Context mContext;
    private EditText emailEditText, passwordEditText;

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
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Load the components to the variables
        registerNow = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);

        registerNow.setOnClickListener(v -> {
            moveActivity(this, RegisterActivity.class);
            viewToast(this, "Register akun anda");
        });

        loginButton.setOnClickListener(v -> {
            moveActivity(this, MainActivity.class);
            viewToast(this, "Selamat datang");
        });

        mContext = this;
        mApiService = UtilsApi.getApiService();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> handleLogin());
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
     * Display a toast message.
     *
     * @param ctx     The context from which the toast should be displayed.
     * @param message The message to show in the toast.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handle the login process.
     */
    protected void handleLogin() {
        String emailStr = emailEditText.getText().toString();
        String passwordStr = passwordEditText.getText().toString();

        if (emailStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.login(emailStr, passwordStr).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Account> res = response.body();

                if (res.success) {
                    loggedAccount = res.payload;
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(mContext, "Selamat datang " + loggedAccount.name, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
