package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing.MainActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Facility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderBusActivity extends AppCompatActivity {

    private TextView busType, price, seatLeft, deptStation, arrStation, facilities, busName;
    private GridLayout scheduleContainer, seatContainer;
    private Button continuePaymentButton;
    private RadioGroup rg;
    public static String facilitiesList;
    private List<CheckBox> seatCheckBoxes = new ArrayList<>();
    public static List<String> seatSelected = new ArrayList<>();
    public static Timestamp scheduleSelected;
    public static double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bus);
        getSupportActionBar().setTitle("Order Bus");
        getter();
        setter();
        handleSchedule();
        handleSeatBookedPerSchedule();
        handleContinueButton();
        handleStringFacility();
    }

    protected void handleStringFacility(){
        for (Facility facility : MainActivity.busSelected.facilities) {
            if(MainActivity.busSelected.facilities.indexOf(facility) == MainActivity.busSelected.facilities.size() - 1) {
                facilitiesList += facility.toString();
            } else {
                facilitiesList += facility.toString() + ", ";
            }
        }
    }

    private void getter() {
        busName = findViewById(R.id.orderbus_busname);
        busType = findViewById(R.id.orderbus_bustype);
        price = findViewById(R.id.orderbus_price);
        seatLeft = findViewById(R.id.orderbus_seatleft);
        deptStation = findViewById(R.id.orderbus_departure_station);
        arrStation = findViewById(R.id.orderbus_arrival_station);
        facilities = findViewById(R.id.orderbus_facilities);
        scheduleContainer = findViewById(R.id.orderbus_schedule);
        seatContainer = findViewById(R.id.orderbus_seat);
        continuePaymentButton = findViewById(R.id.orderbus_button);
    }

    private void setter() {
        busName.setText(MainActivity.busSelected.name);
        busType.setText(MainActivity.busSelected.busType.toString());
        price.setText("Rp " + String.valueOf(MainActivity.busSelected.price.price));
        //TODO: urus seat dulu baru baca dari Response untuk seat left
//        seatLeft.setText(MainActivity.busSelected.capacity);
        deptStation.setText(MainActivity.busSelected.departure.stationName);
        arrStation.setText(MainActivity.busSelected.arrival.stationName);
        facilities.setText(facilitiesList);
    }

    protected void handleSchedule() {
        List<String> scheduleList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (int i = 0; i < MainActivity.busSelected.schedules.size(); i++) {
            scheduleList.add(sdf.format(MainActivity.busSelected.schedules.get(i).departureSchedule));
        }

        if (scheduleList.size() == 0) {
            TextView tv = new TextView(this);
            tv.setText("No schedule available");
            scheduleContainer.addView(tv);
            continuePaymentButton.setEnabled(false);
            return;
        }

        rg = new RadioGroup(this);
        for (int i = 0; i < scheduleList.size(); i++) {
            RadioButton btn = new RadioButton(this);
            btn.setText(scheduleList.get(i));
            btn.setId(i);
            rg.addView(btn);
        }
        scheduleContainer.addView(rg);
    }

    protected void handleSeatBookedPerSchedule() {
        for (int i = 0; i < MainActivity.busSelected.capacity; i++) {
            CheckBox cb = new CheckBox(this);
            if (i < 10) {
                cb.setText(String.valueOf("RS0" + (i + 1)));
            } else {
                cb.setText(String.valueOf("RS" + (i + 1)));
            }
            seatContainer.addView(cb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                scheduleSelected = new Timestamp(MainActivity.busSelected.schedules.get(i).departureSchedule.getTime());
                seatContainer.removeAllViews();
                MainActivity.busSelected.schedules.forEach(schedule -> {
                    if (schedule.departureSchedule.equals(MainActivity.busSelected.schedules.get(i).departureSchedule)) {
                        schedule.seatAvailability.forEach((seat, isAvailable) -> {
                            CheckBox cb = new CheckBox(OrderBusActivity.this);
                            cb.setText(seat);
                            cb.setEnabled(isAvailable);
                            seatContainer.addView(cb);
                        });
                    }
                });
            }
        });
    }

    protected void handleContinueButton(){
        continuePaymentButton.setOnClickListener(v -> {
            seatSelected.clear();
            seatCheckBoxes.clear();
            for (int i = 0; i < seatContainer.getChildCount(); i++) {
                seatCheckBoxes.add((CheckBox) seatContainer.getChildAt(i));
            }
            seatCheckBoxes.forEach(seatCheckBox -> {
                if (seatCheckBox.isChecked()) {
                    seatSelected.add(seatCheckBox.getText().toString());
                }
            });
            if (seatSelected.size() == 0 || scheduleSelected == null) {
                Toast.makeText(this, "Please select at least one schedule or seat", Toast.LENGTH_SHORT).show();
                return;
            }

            totalPrice = MainActivity.busSelected.price.price * seatSelected.size();
            Intent intent = new Intent(this, MakeBookingActivity.class);
            finish();
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}