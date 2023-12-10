package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton = null;
    private TextView registerButton = null;
    private EditText emailInput, passwordInput;
    private BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mApiService = UtilsApi.getAPIService();

        registerButton = findViewById(R.id.login_creaeteaccount);
        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.login_email);
        passwordInput = findViewById(R.id.login_password);

        registerButton.setOnClickListener(v -> moveActivity(LoginActivity.this, RegisterActivity.class));

        checkIfAlreadyLogin();
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    viewToast(LoginActivity.this, "Email or Password is Empty");
                    return;
                }

                handleLogin(email, password);
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

    protected void handleLogin(String email, String password){
        mApiService.login(email, password).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if(response.body().success && response.isSuccessful()){
                    viewToast(LoginActivity.this, "Success");
                    LoggedAccount.loggedAccount = response.body().payload;
                    saveCredential(email, password);
                    System.out.println(LoggedAccount.loggedAccount.id);
                    moveActivity(LoginActivity.this, MainActivity.class);
                }else{
                    viewToast(LoginActivity.this, "Error");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                viewToast(LoginActivity.this, "Error");
            }
        });
    }

    protected void saveCredential(String email, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    protected void checkIfAlreadyLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        if(!email.isEmpty() && !password.isEmpty()){
            handleLogin(email, password);
            System.out.println("EMAIL: " + email + " PASSWORD: " + password);
        }
    }
}