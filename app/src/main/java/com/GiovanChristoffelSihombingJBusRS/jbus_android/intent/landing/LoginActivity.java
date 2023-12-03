package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

        registerButton = findViewById(R.id.create_account);
        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.login_input_email);
        passwordInput = findViewById(R.id.login_input_password);

        registerButton.setOnClickListener(v -> moveActivity(LoginActivity.this, RegisterActivity.class));
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
//        AccountLogin accountLogin = new AccountLogin(email, password);
//        System.out.println(accountLogin.email + " " + accountLogin.password);
        mApiService.login(email, password).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if(response.body().success && response.isSuccessful()){
                    viewToast(LoginActivity.this, "Success");

//                    TODO: STATIC-in Account Logged nya, jangan pakai shared preferences
                    LoggedAccount.loggedAccount = response.body().payload;
                    System.out.println(LoggedAccount.loggedAccount.id);
//                    SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("id", response.body().payload.id);
//                    editor.putString("name", response.body().payload.name);
//                    editor.putString("email", response.body().payload.email);
//                    editor.putString("password", response.body().payload.password);
//                    editor.putFloat("balance", (float)response.body().payload.balance);
//                    editor.apply();
//                    finish();
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
}