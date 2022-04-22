package com.example.dummyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] provinceListFr = new String[]{
            "Québec", "Ontario", "Nouvelle-Écosse", "Nouveau-Brunswick", "Manitoba", "Colombie-Britannique",
            "Ile-du-Prince-Édouard", "Saskatchewan", "Alberta", "Terre-Neuve-et-Labrador",
            "Territoires du Nord-Ouest", "Yukon", "Nunavut"
    };
    String[] provinceListEn = new String[]{
            "Quebec", "Ontario", "Nova Scotia", "New Brunswick", "Manitoba", "British Columbia",
            "Prince Edward Island", "Saskatchewan", "Alberta", "Newfoundland and Labrador",
            "Northwest Territories", "Yukon", "Nunavut"
    };

    private String[] provinceCode = new String[]{
            "QC", "ON", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL", "NT", "YT", "NU"
    };

    RecyclerView recyclerView;
    CustomAdapter recyclerViewAdapter;

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

        recyclerView = findViewById(R.id.lstCity);

        recyclerViewAdapter = new CustomAdapter(this, new String[]{});
        recyclerView.setAdapter(recyclerViewAdapter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.CENTER);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String code = provinceCode[i];

        WebAPI api = new WebAPI();
        try {
            List<City> cities = api.getCityList();
            List<City> provinceCities = cities.stream().filter(city -> city.province.equalsIgnoreCase(code))
                    .collect(Collectors.toList());


            recyclerViewAdapter.setData(provinceCities);
            recyclerViewAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}