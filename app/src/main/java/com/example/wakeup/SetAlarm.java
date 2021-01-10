package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SetAlarm extends AppCompatActivity {

    EditText MinuteEditText;

    Button CreateAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

//        EditText HourEditText = findViewById(R.id.Hours);
    }
}