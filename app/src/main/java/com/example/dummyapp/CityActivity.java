package com.example.dummyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class CityActivity extends AppCompatActivity {

    TextView city, temperature, feelslike, wind;
    String cityD, code, province;
    Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        city = findViewById(R.id.txt_name);
        temperature = findViewById(R.id.txt_temperature);
        wind = findViewById(R.id.txt_wind);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("nameFr")) {
            code = getIntent().getStringExtra("code");
            province = getIntent().getStringExtra("province");
            cityD = getIntent().getStringExtra("nameFr");
            WebAPI api = new WebAPI(this);
            try {
                weather = api.getWeatherData(province, code);
                Toast.makeText(this, "Temperature: " + weather.temperature, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        city.setText(cityD);
        temperature.setText(weather.temperature + "C");
        wind.setText(weather.wind.speed + " " + weather.wind.direction);
    }
}