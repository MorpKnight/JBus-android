package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;

public class MakeBookingActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_booking);
        radioGroup = findViewById(R.id.make_booking_radiogroup);

        for(int i = 0; i < 5; i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("Radio Button " + i);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
        });
    }
}