package com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;

import java.util.List;

public class ManageBusAdapter extends ArrayAdapter<Bus> {
    public ManageBusAdapter(Context context, List<Bus> resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bus bus = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_manage_bus, parent, false);
        }

        TextView tvBusName = convertView.findViewById(R.id.manageBus_busName);
        tvBusName.setText(bus.name);

        return convertView;
    }
}
