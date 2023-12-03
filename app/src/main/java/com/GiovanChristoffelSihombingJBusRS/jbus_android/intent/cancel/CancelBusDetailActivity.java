package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.cancel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Schedule;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelBusDetailActivity extends AppCompatActivity {
    private Button cancelScheduleButton, cancelBusButton;
    private GridLayout gridLayout;
    private List<Schedule> selectedSchedule = new ArrayList<>();
    private TextView busName;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_bus_detail);
        getSupportActionBar().setTitle("Delete Schedule/Bus");
        getter();
    }

    protected void getter(){
        cancelScheduleButton = findViewById(R.id.cancelbus_delete_schedule);
        cancelBusButton = findViewById(R.id.cancelbus_delete_bus);
        gridLayout = findViewById(R.id.cancelbus_gridlayout);
        busName = findViewById(R.id.cancelbus_busname);
        mApiService = UtilsApi.getAPIService();

        busName.setText(CancelBusActivity.renterSelectedBus.name);
        gridLayout.setColumnCount(2);
        handleSchedule();
        deleteBus();
        deleteSelectedSchedule();
    }

    protected void handleSchedule(){
        List<Schedule> scheduleList = CancelBusActivity.renterSelectedBus.schedules;
        for(Schedule schedule: scheduleList){
            CheckBox checkBox = new CheckBox(this);
            String timeFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            String formattedTime = sdf.format(schedule.departureSchedule);
            checkBox.setText(formattedTime);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        selectedSchedule.add(schedule);
                    } else {
                        selectedSchedule.remove(schedule);
                    }
                }
            });

            gridLayout.addView(checkBox);
        }
    }

    protected void deleteSelectedSchedule(){
        cancelScheduleButton.setOnClickListener(view -> {
            // TODO: mApiService untuk delete schedule
            selectedSchedule.forEach(schedule -> {
                String timeFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
                String formattedTime = sdf.format(schedule.departureSchedule);
                mApiService.deleteSchedule(CancelBusActivity.renterSelectedBus.id, formattedTime).enqueue(new Callback<BaseResponse<Bus>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(CancelBusDetailActivity.this, "Schedule deleted", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(CancelBusDetailActivity.this, CancelBusActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                        Toast.makeText(CancelBusDetailActivity.this, "Schedule failed to delete", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            Toast.makeText(this, "Schedule deleted: " + selectedSchedule.size(), Toast.LENGTH_SHORT).show();
        });
    }

    protected void deleteBus() {
        cancelBusButton.setOnClickListener(view -> {
            mApiService.delete(CancelBusActivity.renterSelectedBus.id).enqueue(new Callback<BaseResponse<Bus>>() {
                @Override
                public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(CancelBusDetailActivity.this, "Bus deleted", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(CancelBusDetailActivity.this, CancelBusActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                    Toast.makeText(CancelBusDetailActivity.this, "Bus failed to delete", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}