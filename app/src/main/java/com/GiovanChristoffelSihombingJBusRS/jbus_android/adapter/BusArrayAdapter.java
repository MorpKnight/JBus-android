package com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {
    public BusArrayAdapter(Context context, List<Bus> buses) {
        super(context, 0, buses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bus bus = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_bus_view, parent, false);
        }

        TextView tvBusName = convertView.findViewById(R.id.busName);

        tvBusName.setText(bus.name);
        return convertView;
    }
}
