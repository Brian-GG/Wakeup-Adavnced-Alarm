package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setalarm = (Button) findViewById(R.id.button_setalarm);
        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetAlarm();
            }

        });
    }
    public void openSetAlarm() {
        Intent intent = new Intent(this, SetAlarm.class);
        startActivity(intent);
    }
}