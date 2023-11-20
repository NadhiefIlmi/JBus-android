package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Initialize components
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);

        // Replace "Your Name" with your actual name
        usernameTextView.setText("Nadhif");

        // Replace "your_email@example.com" with your actual email address
        emailTextView.setText("Nadhif@mail.com");

        // Replace "5000" with your initial balance
        balanceTextView.setText("IDR 500000000.0");
    }
}