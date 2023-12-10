package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.history;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView busName, busType, busRoute, busFacilities, busSchedule, busSeat, priceperseat, totalPrice, paymentStatus;
    private Button cancelButton;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        getter();
        setter();
        handleCancelButton();
    }

    protected void getter() {
        busName = findViewById(R.id.historydetail_busdetail_busname);
        busType = findViewById(R.id.historydetail_busdetail_bustype);
        busRoute = findViewById(R.id.historydetail_busdetail_route);
        busFacilities = findViewById(R.id.historydetail_busdetail_facilities);
        busSchedule = findViewById(R.id.historydetail_busdetail_schedule);
        busSeat = findViewById(R.id.historydetail_busdetail_seat);
        priceperseat = findViewById(R.id.historydetail_payment_price);
        totalPrice = findViewById(R.id.historydetail_payment_total);
        paymentStatus = findViewById(R.id.historydetail_payment_status);
        cancelButton = findViewById(R.id.historydetail_cancel_button);
        mApiService = UtilsApi.getAPIService();
    }

    protected void setter(){
        busName.setText(HistoryListActivity.paymentHistory.busName);
        busType.setText(HistoryListActivity.paymentHistory.bus.busType.toString());
        busRoute.setText(HistoryListActivity.paymentHistory.route);
        busFacilities.setText(HistoryListActivity.paymentHistory.bus.facilities.toString());
        busSchedule.setText(HistoryListActivity.paymentHistory.payment.departureDate);
//        busSeat.setText(HistoryListActivity.paymentHistory.payment.busSeats.toString());
        priceperseat.setText("Rp " + String.valueOf(HistoryListActivity.paymentHistory.bus.price.price));
//        totalPrice.setText("Rp " + String.valueOf(HistoryListActivity.paymentHistory.totalPrice));
//        paymentStatus.setText(HistoryListActivity.paymentHistory.payment.);
    }

    protected void handleCancelButton(){
        cancelButton.setOnClickListener(v -> {
            //TODO: ERROR HAHAHAHAHA
            mApiService.cancelPayment(0).enqueue(new Callback<BaseResponse<Payment>>() {
                @Override
                public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(HistoryDetailActivity.this, "Payment cancel request sent!", Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(new Intent(HistoryDetailActivity.this, HistoryListActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(HistoryDetailActivity.this, HistoryListActivity.class));
    }
}