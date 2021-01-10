package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class AlarmSet extends AppCompatActivity {

    public TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        timePicker = findViewById(R.id.timPicker);

        final Intent intent = new Intent(this, MyService.class);
        ServiceCaller(intent);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                ServiceCaller(intent);
            }
        });

        Button Back = (Button) findViewById(R.id.button_GoBack);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }

        });

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

