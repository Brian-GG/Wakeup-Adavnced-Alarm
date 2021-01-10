package com.example.liveweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

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
                main = weatherPart.getString("description");

            }

                Log.i("main",main);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}