package com.example.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherOverlay extends AppCompatActivity implements LocationListener, View.OnClickListener {
    private static final int PERMISSION_CODE = 101;
    TextView cityName;
    Button searchButton;
    TextView result;
    String[] permissions_all = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    LocationManager locationManager;
    boolean isGpsLocation;
    boolean isNetworklocation;
    Location loc;
    ProgressDialog progressDialog;
    Button scanBtn;
    ImageView wIcon;


    class Weather extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... address) {

            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream s = connection.getInputStream();
                InputStreamReader sr = new InputStreamReader(s);

                int data = sr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = sr.read();
                }
                return content;


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public void search() {

        result = findViewById(R.id.result);
        wIcon = findViewById(R.id.wIcon);


        double lat = loc.getLatitude();
        double log = loc.getLongitude();
        String lat2 = Double.toString(lat);
        String log2 = Double.toString(log);

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?lat=" + lat2 + "&lon=" + log2 + "&appid=4d801ce314d1d5657fcf0757d0e04c04").get();
            Log.i("contentData", content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");
            String mainTemperature2 = jsonObject.getString("main");
            Log.i("weatherData", weatherData);

            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";
            String temperature = "";
            String feelsLike = "";
            String icon = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
                icon = weatherPart.getString("icon");

            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            double celsius = Double.parseDouble(temperature) - 273.15;
            int cel2 = (int) celsius;
            String cel = Double.toString(cel2);
            Log.i("temperature", cel);

            JSONObject mainPart2 = new JSONObject(mainTemperature2);
            feelsLike = mainPart2.getString("feels_like");
            double celsius2 = Double.parseDouble(feelsLike) - 273.15;
            int cel3 = (int) celsius2;
            String cel4 = Double.toString(cel3);
            Log.i("feelsLike", cel4);


            Log.i("main", main);
            Log.i("description", description);
            Log.i("icon",icon);
            String resultText = "Description: " + description + "\nTemperature: " + cel + "\nFeels Like: " + cel4;
            String imagename = "a" + icon +".png";
            Log.i("main", "hi");
            result.setText(resultText);
            if (icon.equals("01d")){
                wIcon.setImageResource(R.drawable.a01d);
            }
            if (icon.equals("01n")){
                wIcon.setImageResource(R.drawable.a01n);
            }
            if (icon.equals("02d")){
                wIcon.setImageResource(R.drawable.a02d);
            }
            if (icon.equals("02n")){
                wIcon.setImageResource(R.drawable.a02n);
            }
            if (icon.equals("03d")){
                wIcon.setImageResource(R.drawable.a03d);
            }
            if (icon.equals("03n")){
                wIcon.setImageResource(R.drawable.a03n);
            }
            if (icon.equals("04d")){
                wIcon.setImageResource(R.drawable.a04d);
            }
            if (icon.equals("04n")){
                wIcon.setImageResource(R.drawable.a01n);
            }
            if (icon.equals("09d")){
                wIcon.setImageResource(R.drawable.a09d);
            }
            if (icon.equals("09n")){
                wIcon.setImageResource(R.drawable.a09n);
            }
            if (icon.equals("10d")){
                wIcon.setImageResource(R.drawable.a10d);
            }
            if (icon.equals("10n")){
                wIcon.setImageResource(R.drawable.a10n);
            }
            if (icon.equals("11d")){
                wIcon.setImageResource(R.drawable.a11d);
            }
            if (icon.equals("11n")){
                wIcon.setImageResource(R.drawable.a11n);
            }
            if (icon.equals("13n")){
                wIcon.setImageResource(R.drawable.a13n);
            }
            if (icon.equals("13d")){
                wIcon.setImageResource(R.drawable.a13d);
            }
            if (icon.equals("50n")){
                wIcon.setImageResource(R.drawable.a50n);
            }
            if (icon.equals("50d")){
                wIcon.setImageResource(R.drawable.a50d);
            }







        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_overlay);
        // Intent intent = getIntent();
        progressDialog = new ProgressDialog(WeatherOverlay.this);
        progressDialog.setMessage("Fetching location...");
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);


        getLocation();
        search();


        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=4d801ce314d1d5657fcf0757d0e04c04").get();
            Log.i("contentData", content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            Log.i("weatherData", weatherData);

            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String testData = "31424125";
        if (result != null) {
            if (result.getContents() != null) {


                if (testData.equals(result.getContents())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(result.getContents());
                    builder.setTitle("Scanning Result:");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            } else {
                Toast.makeText(this, "No results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                getDeviceLocation();
            } else {
                requestPermission();
            }

        } else {
            getDeviceLocation();
        }


    }

    private boolean checkPermission() {
        for (int i = 0; i < permissions_all.length; i++) {
            int result = ContextCompat.checkSelfPermission(WeatherOverlay.this, permissions_all[i]);
            if (result == PackageManager.PERMISSION_GRANTED) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(WeatherOverlay.this, permissions_all, PERMISSION_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getDeviceLocation() {
        //now all permission part complete now let's fetch location
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGpsLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworklocation = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsLocation && !isNetworklocation) {
            showSettingForLocation();
            getLastlocation();
        } else {
            getFinalLocation();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFinalLocation();
                } else {
                    Toast.makeText(this, "Permission Failed", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getFinalLocation() {
        //one thing i missed in permission let's complete it
        try {
            if (isGpsLocation) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, WeatherOverlay.this);
                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (loc != null) {
                        updateUi(loc);
                    }
                }
            } else if (isNetworklocation) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 1, 10, WeatherOverlay.this);
                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (loc != null) {
                        updateUi(loc);
                    }
                }
            } else {
                Toast.makeText(this, "Can't Get Location", Toast.LENGTH_SHORT).show();
            }

        } catch (SecurityException e) {
            Toast.makeText(this, "Can't Get Location", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateUi(Location loc) {
        if (loc.getLatitude() == 0 && loc.getLongitude() == 0) {
            getDeviceLocation();
        } else {
            progressDialog.dismiss();
            String lat = Double.toString(loc.getLatitude());
            String log = Double.toString(loc.getLongitude());
            Log.i("lat", lat);
            Log.i("log", log);


        }

    }


    private void getLastlocation() {
        if (locationManager != null) {
            try {
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSettingForLocation() {
        AlertDialog.Builder al = new AlertDialog.Builder(WeatherOverlay.this);
        al.setTitle("Location Not Enabled!");
        al.setMessage("Enable Location ?");
        al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        al.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        al.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(Location location) {
        updateUi(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}