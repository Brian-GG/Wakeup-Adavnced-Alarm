package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AlarmSet = (Button) findViewById(R.id.button_setalarm);
        AlarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmSet();
            }

        });
    }
    public void openAlarmSet() {
        Intent intent = new Intent(this, AlarmSet.class);
        startActivity(intent);
    }






}