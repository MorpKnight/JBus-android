package com.GiovanChristoffelSihombingJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    public TextView aboutMeName, aboutMeEmail, aboutMeBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        aboutMeName = findViewById(R.id.about_me_name_title);
        aboutMeEmail = findViewById(R.id.about_me_email);
        aboutMeBalance = findViewById(R.id.about_me_balance);

        aboutMeName.setText("morpknight");
        aboutMeEmail.setText("christoffelsihombing@gmail.com");
        aboutMeBalance.setText("Rp. 100.000");
    }
}