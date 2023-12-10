package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing.MainActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.MakeBooking;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeBookingActivity extends AppCompatActivity {
    private TextView busName, busType, departure, arrival, facilities, schedule, seat, priceperseat, coupon, totalprice, balance;
    private Button button;
    private EditText couponCode;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);
        getSupportActionBar().setTitle("Make Booking");
        getter();
        setter();

        button.setOnClickListener(v -> {
            paymentHandler();
        });
    }

    protected void getter() {
        busName = findViewById(R.id.makebooking_busdetail_busname);
        busType = findViewById(R.id.makebooking_busdetail_bustype);
        departure = findViewById(R.id.makebooking_busdetail_departure);
        arrival = findViewById(R.id.makebooking_busdetail_arrival);
        facilities = findViewById(R.id.makebooking_busdetail_facilities);
        schedule = findViewById(R.id.makebooking_orderdetail_schedule);
        seat = findViewById(R.id.makebooking_orderdetail_seat);
        priceperseat = findViewById(R.id.makebooking_summary_price);
        coupon = findViewById(R.id.makebooking_summary_coupon);
        totalprice = findViewById(R.id.makebooking_summary_total);
        button = findViewById(R.id.makebooking_button);
        couponCode = findViewById(R.id.makebooking_coupon);
        balance = findViewById(R.id.makebooking_payment_balance);
        mApiService = UtilsApi.getAPIService();
    }

    protected void setter() {
        busName.setText(MainActivity.busSelected.name);
        busType.setText(MainActivity.busSelected.busType.toString());
        departure.setText(MainActivity.busSelected.departure.stationName);
        arrival.setText(MainActivity.busSelected.arrival.stationName);
        facilities.setText(OrderBusActivity.facilitiesList);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        schedule.setText(sdf.format(OrderBusActivity.scheduleSelected));

        String seats = "";
        for (String s : OrderBusActivity.seatSelected) {
            if(OrderBusActivity.seatSelected.indexOf(s) == OrderBusActivity.seatSelected.size() - 1) {
                seats += s;
            } else {
                seats += s + ", ";
            }
        }
        seat.setText(seats);
        priceperseat.setText("Rp " + String.valueOf(MainActivity.busSelected.price.price));
        coupon.setText("Rp " + "0");
        totalprice.setText("Rp " + String.valueOf(OrderBusActivity.totalPrice));
        balance.setText("Rp " + String.valueOf(LoggedAccount.loggedAccount.balance));
        if (LoggedAccount.loggedAccount.balance < MainActivity.busSelected.price.price) {
            button.setEnabled(false);
            button.setText("Insufficient Balance");
        }
    }

    protected void couponHandler() {
        couponCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == 6) {
                if (couponCode.getText().toString().equals("JBUS")) {
                    coupon.setText("Rp " + String.valueOf(OrderBusActivity.totalPrice * 0.1));
                    totalprice.setText("Rp " + String.valueOf(OrderBusActivity.totalPrice * 0.9));
                    Toast.makeText(this, "Coupon Applied", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Invalid Coupon", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });
    }

    protected void paymentHandler() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(OrderBusActivity.scheduleSelected);
        System.out.println(LoggedAccount.loggedAccount.id);
        mApiService.makeBooking(LoggedAccount.loggedAccount.id, MainActivity.busSelected.accountId, MainActivity.busSelected.id, OrderBusActivity.seatSelected, date).enqueue(new Callback<BaseResponse<MakeBooking>>() {
            @Override
            public void onResponse(Call<BaseResponse<MakeBooking>> call, Response<BaseResponse<MakeBooking>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MakeBookingActivity.this, "Booking Success", Toast.LENGTH_SHORT).show();
                    LoggedAccount.loggedAccount = response.body().payload.account;
                    finish();
                    startActivity(new Intent(MakeBookingActivity.this, MainActivity.class));
                    System.out.println(response.body().payload.payment.toString());
                } else {
                    Toast.makeText(MakeBookingActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MakeBooking>> call, Throwable t) {
                Toast.makeText(MakeBookingActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}