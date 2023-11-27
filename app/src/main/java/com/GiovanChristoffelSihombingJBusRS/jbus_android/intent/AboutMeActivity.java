package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
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
        renterButton = findViewById(R.id.renterContainer);
        renterTitle = findViewById(R.id.aboutme_renter);
        renterMessage = findViewById(R.id.aboutme_renter_footer);
        mApiService = UtilsApi.getAPIService();

//        TODO: PAKAI STATIC DULU, jangan pakai shared preference
//        SharedPreferences sh = getSharedPreferences("account", MODE_PRIVATE);
//        name = sh.getString("name", "");
//        email = sh.getString("email", "");
//        balance = sh.getFloat("balance", 0);
//        id = sh.getInt("id", 0);
        id = LoggedAccount.loggedAccount.id;
        name = LoggedAccount.loggedAccount.name;
        email = LoggedAccount.loggedAccount.email;
        balance = (float) LoggedAccount.loggedAccount.balance;
        String balanceString = String.format("Rp. %s", balance);

        aboutMeName.setText(name);
        aboutMeEmail.setText(email);
        aboutMeBalance.setText(balanceString);
        nameInitial.setText(name.substring(0, 1));

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
                    viewToast(AboutMeActivity.this, "Please fill the top up amount");
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
                else moveActivity(AboutMeActivity.this, ManageBusActivity.class);
            }
        });
    }

    protected void handleTopUp(Double value) {
        mApiService.topUp(id, value).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                viewToast(AboutMeActivity.this, String.format("Top Up Success, new balance: Rp. %s", response.body().payload));
//                SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putFloat("balance", response.body().payload.floatValue());
//                editor.apply();
                LoggedAccount.loggedAccount.balance = response.body().payload;
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

    protected void handleRenter() {
        moveActivity(AboutMeActivity.this, RegisterRenterActivity.class);
        return;
    }

    private void viewToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}