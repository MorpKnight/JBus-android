package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.cancel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.ManageBusAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelBusActivity extends AppCompatActivity {
    private ListView listView;
    private List<Bus> busList;
    private ManageBusAdapter manageBusAdapter;
    public static Bus renterSelectedBus;
    private BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_bus);
        getSupportActionBar().setTitle("Cancel Bus");
        mApiService = UtilsApi.getAPIService();
        listView = findViewById(R.id.listview_cancelbus);
        getAllBus();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            renterSelectedBus = (Bus) parent.getItemAtPosition(position);
            Intent intent = new Intent(CancelBusActivity.this, CancelBusDetailActivity.class);
            finish();
            startActivity(intent);
        });
    }

    protected void getAllBus(){
        mApiService.getMyBus(LoggedAccount.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                busList = response.body();
                manageBusAdapter = new ManageBusAdapter(CancelBusActivity.this, busList);
                listView.setAdapter(manageBusAdapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }
}