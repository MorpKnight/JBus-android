package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.renter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.ManageBusAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing.AboutMeActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage.AddBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage.AddStationActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage.BusScheduleActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {

    private MenuItem addBus, addStation;
    private ListView listView;
    private List<Bus> busList;
    private ManageBusAdapter manageBusAdapter;
    private ImageView add_edit_BusScheduleCalendar;
    public static Bus renterSelectedBus;
    private BaseAPIService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);
        getSupportActionBar().setTitle("Manage Bus");

        listView = findViewById(R.id.listView_manageBus);
        mApiService = UtilsApi.getAPIService();
        add_edit_BusScheduleCalendar = findViewById(R.id.add_edit_ScheduleAdapter);

        handleGetAll();
        if (busList != null) {
            manageBusAdapter = new ManageBusAdapter(ManageBusActivity.this, busList);
            listView.setAdapter(manageBusAdapter);
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ImageView temp = view.findViewById(R.id.add_edit_ScheduleAdapter);
            renterSelectedBus = (Bus) parent.getItemAtPosition(position);
            temp.setOnClickListener(v -> {
                Intent intent = new Intent(ManageBusActivity.this, BusScheduleActivity.class);
                finish();
                startActivity(intent);
            });
        });
    }

    protected void handleGetAll() {
        mApiService.getMyBus(LoggedAccount.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                busList = response.body();
                manageBusAdapter = new ManageBusAdapter(ManageBusActivity.this, busList);
                listView.setAdapter(manageBusAdapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_manage_bus, menu);

        addBus = menu.findItem(R.id.addBus);
        addStation = menu.findItem(R.id.addStation);

        addBus.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ManageBusActivity.this, AddBusActivity.class);
                finish();
                startActivity(intent);
                return false;
            }
        });

        addStation.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                finish();
                startActivity(new Intent(ManageBusActivity.this, AddStationActivity.class));
                return false;
            }
        });

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}