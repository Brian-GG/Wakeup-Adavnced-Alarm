package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

public class AlarmSet extends AppCompatActivity {

    public TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);

        timePicker = findViewById(R.id.timPicker);

        final Intent intent = new Intent(this, MyService.class);
        ServiceCaller(intent);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                ServiceCaller(intent);
            }
        });

    }

    private void ServiceCaller(Intent intent) {

        stopService(intent);

        Integer alarmHour = timePicker.getCurrentHour();
        Integer alarmMinute = timePicker.getCurrentMinute();

        intent.putExtra("alarmHour", alarmHour);
        intent.putExtra("alarmMinute", alarmMinute);

        startService(intent);
    }
}

