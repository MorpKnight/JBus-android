package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;

public class MakeBookingActivity extends AppCompatActivity {
    private LinearLayout toggleCalendarLayout;
    private Button makeBookingButton, viewToastButton;
    private TimePicker timePicker;
    private CalendarView calendarView;
    private String selectedDate, selectedTime, combinedTIME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);

        toggleCalendarLayout = findViewById(R.id.make_booking_toggle_calendar);
        makeBookingButton = findViewById(R.id.make_booking_button);
        calendarView = findViewById(R.id.make_booking_calendar);
        timePicker = findViewById(R.id.make_booking_time_picker);
        viewToastButton = findViewById(R.id.make_booking_view_toast);

        toggleCalendarLayout.setVisibility(LinearLayout.GONE);

        makeBookingButton.setOnClickListener(v -> {
            if (toggleCalendarLayout.getVisibility() == LinearLayout.GONE) {
                toggleCalendarLayout.setVisibility(LinearLayout.VISIBLE);
            } else {
                toggleCalendarLayout.setVisibility(LinearLayout.GONE);
            }
        });

        viewToastButton.setOnClickListener(v -> {
            combinedTIME = selectedDate + " " + selectedTime;
            Toast.makeText(getApplicationContext(), combinedTIME, Toast.LENGTH_SHORT).show();
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                selectedTime = i  + ":" + i1;
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDate = i + "=" + (i1 + 1) + "-" + i2;
            }
        });
    }
}