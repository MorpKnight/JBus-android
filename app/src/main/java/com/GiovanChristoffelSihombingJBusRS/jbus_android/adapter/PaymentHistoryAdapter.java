package com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.PaymentHistory;

import java.text.SimpleDateFormat;
import java.util.List;

public class PaymentHistoryAdapter extends ArrayAdapter<PaymentHistory> {
    public PaymentHistoryAdapter(@NonNull Context context, @NonNull List<PaymentHistory> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PaymentHistory paymentHistory = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_payment_history, parent, false);
        }
        TextView busName = convertView.findViewById(R.id.historylist_busName);
        TextView route = convertView.findViewById(R.id.historylist_busRoute);
        TextView date = convertView.findViewById(R.id.historylist_datePurchase);
        TextView price = convertView.findViewById(R.id.historylist_price);
        busName.setText(paymentHistory.busName);
        route.setText(paymentHistory.route);
        date.setText(paymentHistory.date);
        price.setText("Rp. " + paymentHistory.price);
        return convertView;
    }
}
