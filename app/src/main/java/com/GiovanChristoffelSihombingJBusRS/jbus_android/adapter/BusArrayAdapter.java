package com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Facility;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {
    public BusArrayAdapter(Context context, List<Bus> buses) {
        super(context, 0, buses);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bus bus = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_bus_view, parent, false);
        }

        TextView busName = convertView.findViewById(R.id.busNameAdapter);
        TextView busDeparture = convertView.findViewById(R.id.busDepartureAdapter);
        TextView busArrival = convertView.findViewById(R.id.busArrivalAdapter);

        busName.setText(bus.name);
        busDeparture.setText(bus.departure.stationName);
        busArrival.setText(bus.arrival.stationName);

        return convertView;
    }
}
