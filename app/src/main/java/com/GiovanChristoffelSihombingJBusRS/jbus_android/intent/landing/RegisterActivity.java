package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText;
    private BaseAPIService mApiService;
    private Button registerButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mApiService = UtilsApi.getAPIService();
        emailEditText = findViewById(R.id.register_email);
        usernameEditText = findViewById(R.id.register_name);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(email.isEmpty() || username.isEmpty() || password.isEmpty()){
                viewToast(RegisterActivity.this, "Email, Username, or Password is Empty");
                return;
            }

            handleRegister(email, username, password);
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    protected void handleRegister(String email, String username, String password){
        mApiService.register(username, email, password).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if(response.body().success && response.isSuccessful()){
                    viewToast(RegisterActivity.this, "Register Success");
                    LoggedAccount.loggedAccount = response.body().payload;
//                    SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("id", response.body().payload.id);
//                    editor.putString("name", response.body().payload.name);
//                    editor.putString("email", response.body().payload.email);
//                    editor.putString("password", response.body().payload.password);
//                    editor.putFloat("balance", (float)response.body().payload.balance);
//                    editor.commit();

                    moveActivity(RegisterActivity.this, MainActivity.class);
                }else{
                    viewToast(RegisterActivity.this, "Register Failed");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                viewToast(RegisterActivity.this, "Register Failed onFailure");
            }
        });
    }
}