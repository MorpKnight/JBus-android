package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderBusActivity extends AppCompatActivity {

    private TextView deptStation, arrStation, facility, price, busType, busName;
    private Spinner deptTime;
    private List<String> facilityList = new ArrayList<String>();
    private List<String> timeList = new ArrayList<String>();
    private Button bookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bus);

        busName = findViewById(R.id.info_bus_bus_name);
        deptStation = findViewById(R.id.info_bus_departure_station);
        arrStation = findViewById(R.id.info_bus_arrival_station);
        deptTime = findViewById(R.id.info_bus_departure_time);
        facility = findViewById(R.id.info_bus_facility);
        bookBtn = findViewById(R.id.info_bus_orderBus);
        price = findViewById(R.id.info_bus_price);
        busType = findViewById(R.id.info_bus_bus_type);

        MainActivity.busSelected.facilities.forEach(facility -> {
            facilityList.add(String.valueOf(facility));
        });

        busName.setText(MainActivity.busSelected.name);
        deptStation.setText(MainActivity.busSelected.departure.stationName);
        arrStation.setText(MainActivity.busSelected.arrival.stationName);
        price.setText(String.valueOf(MainActivity.busSelected.price.price));
        busType.setText(MainActivity.busSelected.busType.toString());
        facility.setText(facilityList.toString());
        handleSpinner();

        bookBtn.setOnClickListener(v -> {

        });
    }

    protected void handleSpinner() {
        if (MainActivity.busSelected.schedules.size() == 0) {
            deptTime.setEnabled(false);
            bookBtn.setEnabled(false);
        }

        MainActivity.busSelected.schedules.forEach(schedule -> {
            String timeFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            String formattedTime = sdf.format(schedule.departureSchedule);
            timeList.add(formattedTime);
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        deptTime.setAdapter(adapter);
    }
}