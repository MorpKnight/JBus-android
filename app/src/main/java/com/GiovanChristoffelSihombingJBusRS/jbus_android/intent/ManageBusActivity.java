package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.ManageBusAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {

    private MenuItem addItem;
    private ListView listView;
    private List<Bus> busList;
    private ManageBusAdapter manageBusAdapter;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);
        getSupportActionBar().setTitle("Manage Bus");

        listView = findViewById(R.id.listView_manageBus);
        mApiService = UtilsApi.getAPIService();

//        TODO: TAMBAH GET BUAT ALL BUS DAN STATION
        handleGetAll();
        if(busList != null){
            manageBusAdapter = new ManageBusAdapter(ManageBusActivity.this, busList);
            listView.setAdapter(manageBusAdapter);
        }
    }

    protected void handleGetAll(){
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

        addItem = menu.findItem(R.id.addBus);

        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ManageBusActivity.this, AddBusActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return false;
    }
}