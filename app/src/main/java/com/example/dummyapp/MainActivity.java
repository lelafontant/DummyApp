package com.example.dummyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewAnimator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {
    String[] provinceListFr = new String[] {
            "Québec", "Ontario", "Manitoba", "Saskatchewan", "Colombie-Britannique", "Ile du Prince", "Yukon"
    };
    String[] provinceListEn = new String[] {
            "Quebec", "Ontario", "Manitoba", "Saskatchewan", "British Columbia", "Ile du Prince", "Yukon"
    };

    private String[] provinceCode = new String[] {
            "QC", "ON", "MN", "SK", "BC", "IPE", "YK"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, provinceListFr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String code = provinceCode[i];

        WebAPI api = new WebAPI();
        try {
            List<City> cities = api.getCityList();
            List<String> provinceList=  new ArrayList();

            cities.forEach(city -> {
                if (city.province.equalsIgnoreCase(code)) {
                    provinceList.add(city.code);
                }
            });

            Toast.makeText(adapterView.getContext(),"Number of city: " + provinceList.size() , Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}