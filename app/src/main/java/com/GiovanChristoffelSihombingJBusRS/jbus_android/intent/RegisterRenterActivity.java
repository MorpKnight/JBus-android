package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Renter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {

    private EditText ET_companyName, ET_companyAddress, ET_companyPhone;
    private String companyName, companyAddress, companyPhone;
    private Button registerButton;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        ET_companyName = findViewById(R.id.register_renter_companyname);
        ET_companyAddress = findViewById(R.id.register_renter_address);
        ET_companyPhone = findViewById(R.id.register_renter_phonenumber);
        registerButton = findViewById(R.id.register_renter_button);
        mApiService = UtilsApi.getAPIService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName = ET_companyName.getText().toString();
                companyAddress = ET_companyAddress.getText().toString();
                companyPhone = ET_companyPhone.getText().toString();

                if (companyName.isEmpty() || companyAddress.isEmpty() || companyPhone.isEmpty()) {
                    viewToast(RegisterRenterActivity.this, "Please fill all the fields");
                    return;
                }

                handleRegisterRenter(companyName, companyAddress, companyPhone);
            }
        });
    }

    private void viewToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    protected void handleRegisterRenter(String companyName, String companyAddress, String companyPhone) {
        mApiService.registerRenter(LoggedAccount.loggedAccount.id, companyName, companyAddress, companyPhone).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {

                viewToast(RegisterRenterActivity.this, "Register Success");

                LoggedAccount.loggedAccount.company = response.body().payload;
                moveActivity(RegisterRenterActivity.this, AboutMeActivity.class);

            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                viewToast(RegisterRenterActivity.this, "Register Failed");
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}