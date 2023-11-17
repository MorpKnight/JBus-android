package com.GiovanChristoffelSihombingJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton = null;
    private TextView registerButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        registerButton = findViewById(R.id.create_account);
        loginButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(v -> moveActivity(LoginActivity.this, RegisterActivity.class));
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = ((TextView) findViewById(R.id.login_email)).getText().toString();
                String password = ((TextView) findViewById(R.id.login_password)).getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    viewToast(LoginActivity.this, "Email or Password is Empty");
                    return;
                }
                viewToast(LoginActivity.this, "Logged");
                moveActivity(LoginActivity.this, MainActivity.class);
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}