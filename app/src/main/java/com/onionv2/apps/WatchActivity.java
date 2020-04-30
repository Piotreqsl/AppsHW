package com.onionv2.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class WatchActivity extends AppCompatActivity {
    ZegarView zegarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        zegarView = findViewById(R.id.zegar);


        findViewById(R.id.systemTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zegarView.setSystemTime();
            }
        });

        findViewById(R.id.customTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(WatchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, selectedHour);
                        c.set(Calendar.MINUTE, selectedMinute);
                        c.set(Calendar.SECOND, 0);
                        zegarView.setCustomTime(c);


                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Wybierz czas");
                mTimePicker.show();
            }
        });

    }



}
