package com.GiovanChristoffelSihombingJBusRS.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    public TextView aboutMeName, aboutMeEmail, aboutMeBalance, nameInitial;
    private String name, email;
    private float balance;
    private View topUpButton;
    private EditText topUpAmount;
    private BaseAPIService mApiService;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        aboutMeName = findViewById(R.id.aboutme_username);
        aboutMeEmail = findViewById(R.id.aboutme_email);
        aboutMeBalance = findViewById(R.id.balance);
        nameInitial = findViewById(R.id.profile_initial);
        topUpButton = findViewById(R.id.top_up);
        topUpAmount = findViewById(R.id.topup_value);
        mApiService = UtilsApi.getAPIService();

        SharedPreferences sh = getSharedPreferences("account", MODE_PRIVATE);
        name = sh.getString("name", "");
        email = sh.getString("email", "");
        balance = sh.getFloat("balance", 0);
        id = sh.getInt("id", 0);
        String balanceString = String.format("Rp. %s", balance);

        aboutMeName.setText(name);
        aboutMeEmail.setText(email);
        aboutMeBalance.setText(balanceString);
        nameInitial.setText(name.substring(0, 1));

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topUpAmount.getText().toString().isEmpty()) {
                    viewToast(AboutMeActivity.this, "Please fill the top up amount");
                    return;
                }
                Double value = Double.parseDouble(topUpAmount.getText().toString());
                handleTopUp(value);
            }
        });
    }

    protected void handleTopUp(Double value) {
        mApiService.topUp(id, value).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                viewToast(AboutMeActivity.this, String.format("Top Up Success, new balance: Rp. %s", response.body().payload));
                SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("balance", response.body().payload.floatValue());
                editor.apply();
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(topUpAmount.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                topUpAmount.setText("");
                aboutMeBalance.setText(String.format("Rp. %s", response.body().payload));
            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                viewToast(AboutMeActivity.this, "Top Up Failed");
            }
        });
    }

    private void viewToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}