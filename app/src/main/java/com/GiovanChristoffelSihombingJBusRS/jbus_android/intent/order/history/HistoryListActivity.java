package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.PaymentHistoryAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.PaymentHistory;

public class HistoryListActivity extends AppCompatActivity {

    private ListView listView;
    private PaymentHistoryAdapter paymentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        getter();
    }

    protected void getter() {
        try {
            listView = findViewById(R.id.historylist_listview);
            for (Payment accountPayment : OrderHistory.accountPayments) {
                for (Bus bus : OrderHistory.buses) {
                    if (accountPayment.busId == bus.id) {
                        System.out.println("Bus name: " + bus.name);
                        PaymentHistory.paymentHistories.add(new PaymentHistory(bus.name, bus.departure.stationName + " - " + bus.arrival.stationName, accountPayment.departureDate, String.valueOf(0)));
                    }
                }
            }
            paymentHistoryAdapter = new PaymentHistoryAdapter(HistoryListActivity.this, PaymentHistory.paymentHistories);
            listView.setAdapter(paymentHistoryAdapter);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}