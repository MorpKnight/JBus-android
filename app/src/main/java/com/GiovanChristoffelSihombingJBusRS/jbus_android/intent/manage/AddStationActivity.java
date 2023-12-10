package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.City;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Station;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStationActivity extends AppCompatActivity {
    private EditText stationName, stationAddress;
    private String city;
    private Spinner stationCity;
    private Button addStationButton;
    private BaseAPIService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);
        getSupportActionBar().setTitle("Add Station");
        getter();
        handleCitySpinner();
        addStationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStation();
            }
        });
    }

    protected void getter(){
        stationName = findViewById(R.id.addstation_stationname);
        stationAddress = findViewById(R.id.addstation_stationaddress);
        stationCity = findViewById(R.id.addstation_stationcity);
        addStationButton = findViewById(R.id.addstation_button);
        mApiService = UtilsApi.getAPIService();
    }

    protected void handleCitySpinner(){
        List<String> cityList = new ArrayList<>();
        for (City city : City.values()){
            cityList.add(city.toString());
        }

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationCity.setAdapter(cityAdapter);

        stationCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                city = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
    }

    protected void handleStation(){
        mApiService.createStation(stationName.getText().toString(), city, stationAddress.getText().toString()).enqueue(new Callback<BaseResponse<Station>>() {
            @Override
            public void onResponse(Call<BaseResponse<Station>> call, Response<BaseResponse<Station>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddStationActivity.this, "Station Added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddStationActivity.this, "Failed to add station", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Station>> call, Throwable t) {
                Toast.makeText(AddStationActivity.this, "Failed to add station", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}