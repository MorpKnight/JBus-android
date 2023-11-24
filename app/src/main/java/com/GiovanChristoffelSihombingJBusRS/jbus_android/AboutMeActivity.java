package com.GiovanChristoffelSihombingJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;

public class AboutMeActivity extends AppCompatActivity {
    public TextView aboutMeName, aboutMeEmail, aboutMeBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        aboutMeName = findViewById(R.id.aboutme_username);
        aboutMeEmail = findViewById(R.id.aboutme_email);
        aboutMeBalance = findViewById(R.id.balance);

        SharedPreferences sh = getSharedPreferences("account", MODE_PRIVATE);
        String name = sh.getString("name", "");
        String email = sh.getString("email", "");

        aboutMeName.setText(name);
        aboutMeEmail.setText(email);
    }
}