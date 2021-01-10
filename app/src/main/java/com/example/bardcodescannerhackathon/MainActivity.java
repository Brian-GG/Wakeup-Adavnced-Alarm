package com.example.bardcodescannerhackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn  = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    scanCode();
    }
    private void scanCode() { // method 1:
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning for Barcode");
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
       String testData = "31424125";
        if (result != null){
            if (result.getContents() != null){


                if (testData.equals(result.getContents())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(result.getContents());
                    builder.setTitle("Scanning Result:");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }





                /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result:");


                AlertDialog dialog = builder.create();
                dialog.show();
          */

            }
            else {
                Toast.makeText(this,  "No results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}