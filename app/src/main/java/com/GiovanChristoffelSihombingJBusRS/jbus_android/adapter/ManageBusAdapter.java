package com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Facility;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

        TextView busName = convertView.findViewById(R.id.busNameAdapter);
        TextView busDeparture = convertView.findViewById(R.id.busDepartureAdapter);
        TextView busArrival = convertView.findViewById(R.id.busArrivalAdapter);
        TextView busSeat = convertView.findViewById(R.id.busSeatAdapter);
        TextView busType = convertView.findViewById(R.id.busTypeAdapter);
        TextView busFacility = convertView.findViewById(R.id.busFacilityAdapter);
        TextView busTimeDeparture = convertView.findViewById(R.id.busDepartureTimeAdapter);

        LinearLayout busTimeContainer = convertView.findViewById(R.id.busDepartureTime);
        // TODO: Masih belum jadi bagian parsingnya. Jadiin listview untuk melihat jadwal yang sudah ada karena berupa list

        if (bus.schedules == null) {
            busTimeContainer.setVisibility(LinearLayout.GONE);
        } else {
            busTimeContainer.setVisibility(LinearLayout.VISIBLE);
            String timestampformat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timestampformat);
        }

//        check if current intent is CancelBusActivity
        if (getContext().getClass().getSimpleName().equals("CancelBusActivity")) {
            convertView.findViewById(R.id.add_edit_ScheduleAdapter).setVisibility(View.GONE);
        }

        busName.setText(bus.name);
        busDeparture.setText(bus.departure.stationName);
        busArrival.setText(bus.arrival.stationName);
        busSeat.setText(String.valueOf(bus.capacity) + " seats");
        busType.setText(String.valueOf(bus.busType).replace("_", " "));
        String facilities = "";
        for (Facility facility : bus.facilities) {
//            check if last index
            if (bus.facilities.indexOf(facility) == bus.facilities.size() - 1) {
                facilities += facility.toString().replace("_", " ");
                break;
            }
            facilities += facility.toString().replace("_", " ") + ", ";
        }

        busFacility.setText(facilities);

        return convertView;
    }
}
