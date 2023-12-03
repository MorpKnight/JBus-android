package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.renter.ManageBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusScheduleActivity extends AppCompatActivity {
    private LinearLayout date_time_container;
    private Button add_date_time_button;
    private CalendarView calendarView;
    private TimePicker timePicker;
    private String selectedDate, selectedTime, timeDateCombined;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        date_time_container = findViewById(R.id.add_edit_schedule_date_time_container);
        add_date_time_button = findViewById(R.id.add_edit_schedule_button);
        calendarView = findViewById(R.id.add_edit_schedule_date);
        timePicker = findViewById(R.id.add_edit_schedule_time);
        mApiService = UtilsApi.getAPIService();
//        select time now to selectedTime
        selectedTime = timePicker.getHour() + ":" + timePicker.getMinute() + ":00";

        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            selectedDate = i + "-" + (i1 + 1) + "-" + i2;
        });

        timePicker.setOnTimeChangedListener((timePicker, i, i1) -> {
            selectedTime = i + ":" + i1;
        });

        add_date_time_button.setOnClickListener(v -> {
            timeDateCombined = selectedDate + " " + selectedTime + ":00";
            System.out.println(timeDateCombined);
            mApiService.addSchedule(ManageBusActivity.renterSelectedBus.id, timeDateCombined).enqueue(new Callback<BaseResponse<Bus>>() {
                @Override
                public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(BusScheduleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        System.out.println(response.body().payload);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                    Toast.makeText(BusScheduleActivity.this, "Failed to add schedule", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}