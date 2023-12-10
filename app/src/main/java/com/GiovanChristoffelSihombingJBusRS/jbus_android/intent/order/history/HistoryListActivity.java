package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.history;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.PaymentHistoryAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing.MainActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.PaymentHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryListActivity extends AppCompatActivity {

    private ListView listView;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private BaseAPIService mApiService;
    public static PaymentHistory paymentHistory;
    public static Payment payment;
    private List<Payment> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        getSupportActionBar().setTitle("History");
        PaymentHistory.paymentHistories.clear();

        try {
            getter();
        } catch (Exception e) {
            Toast.makeText(HistoryListActivity.this, "No payment found", Toast.LENGTH_SHORT).show();
        }

        try {
            listView.setOnItemClickListener((parent, view, position, id) -> {
                paymentHistory = (PaymentHistory) parent.getItemAtPosition(position);
                Intent intent = new Intent(HistoryListActivity.this, HistoryDetailActivity.class);
                finish();
                startActivity(intent);
            });
        } catch (Exception e) {
            Toast.makeText(HistoryListActivity.this, "No payment found", Toast.LENGTH_SHORT).show();
        }
    }

    protected void getter() {
        mApiService = UtilsApi.getAPIService();
        listView = findViewById(R.id.historylist_listview);
        mApiService.getMyPayment(LoggedAccount.loggedAccount.id).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                for (Payment accountPayment : response.body()) {
                    for (Bus bus : OrderHistory.buses) {
                        if (accountPayment.busId == bus.id) {
                            System.out.println("Payment: " + accountPayment);
                            PaymentHistory.paymentHistories.add(new PaymentHistory(bus, accountPayment));
                        }
                    }
                }
                paymentHistoryAdapter = new PaymentHistoryAdapter(HistoryListActivity.this, PaymentHistory.paymentHistories);
                listView.setAdapter(paymentHistoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(HistoryListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleListView() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            paymentHistory = (PaymentHistory) parent.getItemAtPosition(position);
            payment = paymentHistory.payment;
            Intent intent = new Intent(HistoryListActivity.this, HistoryDetailActivity.class);
            finish();
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        startActivity(new Intent(HistoryListActivity.this, MainActivity.class));
    }
}