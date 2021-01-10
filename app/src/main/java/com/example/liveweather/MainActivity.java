package com.example.liveweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button searchButton;
    TextView result;

    class Weather extends AsyncTask<String,Void,String>
    {





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
                while (data != -1){
                    ch = (char) data;
                    content = content + ch;
                    data = sr.read();
                }
                return content;


                } catch(IOException e) {
                    e.printStackTrace();
                }

            return null;
        }
    }


    public void search(View view)
    {
        cityName = findViewById(R.id.cityName);
        searchButton = findViewById(R.id.searchbutton);
        result = findViewById(R.id.result);

        String cName = cityName.getText().toString();

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q="+cName+"&APPID=4d801ce314d1d5657fcf0757d0e04c04").get();
            Log.i("contentData",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");
            String mainTemperature2 = jsonObject.getString("main");
            Log.i("weatherData",weatherData);

            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";
            String temperature = "";
            String feelsLike = "";

            for(int i=0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");

            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            double celsius = Double.parseDouble(temperature) - 273.15;
            int cel2 = (int)celsius;
            String cel = Double.toString(cel2);
            Log.i("temperature",cel);

            JSONObject mainPart2 = new JSONObject(mainTemperature2);
            feelsLike = mainPart2.getString("feels_like");
            double celsius2 = Double.parseDouble(feelsLike) - 273.15;
            int cel3 = (int)celsius2;
            String cel4 = Double.toString(cel3);
            Log.i("feelsLike",cel4);






            Log.i("main",main);
            Log.i("description",description);
            String resultText ="Description: "+ description + "\nTemperature: " + cel + "\nFeels Like: " + cel4;
            Log.i("main","hi");
            result.setText(resultText);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=4d801ce314d1d5657fcf0757d0e04c04").get();
            Log.i("contentData",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            Log.i("weatherData",weatherData);

            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";

            for(int i=0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");

            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}