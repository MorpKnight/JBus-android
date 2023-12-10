package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.renter.ManageBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.renter.RegisterRenterActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.cancel.CancelBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    public TextView aboutMeName, aboutMeEmail, aboutMeBalance, nameInitial, renterTitle, renterMessage;
    private String name, email;
    private float balance;
    private View topUpButton, renterButton;
    private LinearLayout cancelBus, cancelApproval;
    private Button logoutButton;
    private EditText topUpAmount;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        getter();
        setter();
        handleIfNotRenter();

        if (LoggedAccount.loggedAccount.company != null) {
            renterTitle.setText("Manage Bus");
            renterMessage.setText("Manage your bus here");
        } else {
            renterTitle.setText("Become Renter");
            renterMessage.setText("Become a renter and manage your bus here");
        }

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topUpAmount.getText().toString().isEmpty()) {
                    Toast.makeText(AboutMeActivity.this, "Please fill the top up amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double value = Double.parseDouble(topUpAmount.getText().toString());
                handleTopUp(value);
            }
        });

        renterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoggedAccount.loggedAccount.company == null) handleRenter();
                else startActivity(new Intent(AboutMeActivity.this, ManageBusActivity.class));
            }
        });

        cancelBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutMeActivity.this, CancelBusActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggedAccount.loggedAccount = null;
                SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", "");
                editor.putString("password", "");
                editor.apply();
                finish();
                startActivity(new Intent(AboutMeActivity.this, LoginActivity.class));
            }
        });
    }

    protected void handleIfNotRenter(){
        if(LoggedAccount.loggedAccount.company == null){
            cancelBus.setVisibility(View.GONE);
            cancelApproval.setVisibility(View.GONE);
        }
    }

    protected void handleTopUp(Double value) {
        mApiService.topUp(LoggedAccount.loggedAccount.id, value).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                Toast.makeText(AboutMeActivity.this, "Top Up Success", Toast.LENGTH_SHORT).show();
                LoggedAccount.loggedAccount.balance = response.body().payload;
                System.out.println(response.body().payload);
                System.out.println(value);
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
                Toast.makeText(AboutMeActivity.this, "Top Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleRenter() {
        startActivity(new Intent(AboutMeActivity.this, RegisterRenterActivity.class));
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    protected void getter() {
        aboutMeName = findViewById(R.id.aboutme_username);
        aboutMeEmail = findViewById(R.id.aboutme_email);
        aboutMeBalance = findViewById(R.id.aboutme_balance_value);
        nameInitial = findViewById(R.id.aboutme_profile_initial);
        topUpButton = findViewById(R.id.aboutme_topup_button);
        topUpAmount = findViewById(R.id.aboutme_topup_value);
        renterButton = findViewById(R.id.aboutme_renter);
        renterTitle = findViewById(R.id.aboutme_renter_title);
        renterMessage = findViewById(R.id.aboutme_renter_desc);
        cancelBus = findViewById(R.id.aboutme_cancelbus);
        cancelApproval = findViewById(R.id.aboutme_approval);
        logoutButton = findViewById(R.id.aboutme_logout_button);
        mApiService = UtilsApi.getAPIService();
    }

    protected void setter() {
        aboutMeName.setText(LoggedAccount.loggedAccount.name);
        aboutMeEmail.setText(LoggedAccount.loggedAccount.email);
        aboutMeBalance.setText(String.format("Rp. %s", LoggedAccount.loggedAccount.balance));
        nameInitial.setText(LoggedAccount.loggedAccount.name.substring(0, 1));
    }
}