package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.renter.ManageBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BusType;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Facility;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Station;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {

    private BaseAPIService mApiService;
    private List<Station> stations = new ArrayList<>();
    private Spinner spinnerBusType, spinnerDeparture, spinnerArrival;
    private Context mContext;
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private AdapterView.OnItemSelectedListener busTypeOISL, departureOISL, arrivalOISL;
    private Button addBusButton;
    private int selectedDeparture, selectedArrival;
    private EditText busName, busCapacity, busPrice;
    private CheckBox AC, WIFI, Toilet, LCD_TV, Lunch, Large_Baggage, CoolBox, Electric_Socket;
    private List<Facility> selectedFacility = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);
        getSupportActionBar().setTitle("Add Bus");
        spinnerDeparture = findViewById(R.id.deptStationSpinner);
        spinnerArrival = findViewById(R.id.arrvStationSpinner);
        addBusButton = findViewById(R.id.addbus_button);
        busName = findViewById(R.id.addbus_busname);
        busCapacity = findViewById(R.id.addbus_buscapacity);
        busPrice = findViewById(R.id.addbus_busprice);
        mContext = this;

        mApiService = UtilsApi.getAPIService();
        handleBusType();
        getStation();

        addBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBox();
                handleAddBus();
            }
        });
    }

    protected void handleCheckBox() {
        selectedFacility.clear();
        AC = findViewById(R.id.facilityAC);
        WIFI = findViewById(R.id.facilityWIFI);
        Toilet = findViewById(R.id.facilityToilet);
        LCD_TV = findViewById(R.id.facilityLCDTV);
        Lunch = findViewById(R.id.facilityLunch);
        Large_Baggage = findViewById(R.id.facilityLargeBaggage);
        CoolBox = findViewById(R.id.facilityCoolbox);
        Electric_Socket = findViewById(R.id.facilityElectricSocket);

        if (AC.isChecked()) {
            selectedFacility.add(Facility.AC);
        }

        if (WIFI.isChecked()) {
            selectedFacility.add(Facility.WIFI);
        }

        if (Toilet.isChecked()) {
            selectedFacility.add(Facility.TOILET);
        }

        if (LCD_TV.isChecked()) {
            selectedFacility.add(Facility.LCD_TV);
        }

        if (Lunch.isChecked()) {
            selectedFacility.add(Facility.LUNCH);
        }

        if (Large_Baggage.isChecked()) {
            selectedFacility.add(Facility.LARGE_BAGGAGE);
        }

        if (CoolBox.isChecked()) {
            selectedFacility.add(Facility.COOL_BOX);
        }

        if (Electric_Socket.isChecked()) {
            selectedFacility.add(Facility.ELECTRIC_SOCKET);
        }
    }

    protected void handleAddBus() {
        mApiService.create(LoggedAccount.loggedAccount.id, busName.getText().toString(), Integer.parseInt(busCapacity.getText().toString()), selectedFacility, selectedBusType, Integer.parseInt(busPrice.getText().toString()), selectedDeparture, selectedArrival).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
//                    TODO: Harusnya pindah ke ManageBusActivity
                    Intent intent = new Intent(AddBusActivity.this, ManageBusActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    protected void getStation() {
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful()) {
                    stations = response.body();
                    List<String> stationName = new ArrayList<>();
                    for (Station station : stations) {
                        stationName.add(station.stationName);
                    }
                    ArrayAdapter<String> adapterDeparture = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, stationName);
                    ArrayAdapter<String> adapterArrival = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, stationName);
                    adapterDeparture.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    adapterArrival.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spinnerDeparture.setAdapter(adapterDeparture);
                    spinnerArrival.setAdapter(adapterArrival);
                    spinnerDeparture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedDeparture = stations.get(i).id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spinnerArrival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedArrival = stations.get(i).id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

            }
        });
    }

    protected void handleBusType() {
        spinnerBusType = findViewById(R.id.busTypeSpinner);
        ArrayAdapter<BusType> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, busType);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerBusType.setAdapter(adapter);
        spinnerBusType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBusType = busType[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(AddBusActivity.this, ManageBusActivity.class));
    }
}