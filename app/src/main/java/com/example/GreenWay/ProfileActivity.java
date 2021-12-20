package com.example.GreenWay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner1;
    JSONArray arrayIntols = null;
    int currentIntol = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DownloadTaskArray task = new DownloadTaskArray();
        ArrayList<String> intolNames = new ArrayList<String>();
        try {
            arrayIntols = task.execute("https://greenwayiade.herokuapp.com/api/intols").get();
            for (int i = 0; i < arrayIntols.length(); i++) {
                intolNames.add(arrayIntols.getJSONObject(i).getString("name"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addItemsOnSpinner(intolNames);
    }

    public void addItemsOnSpinner(List<String> intols) {
        spinner1 = findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, intols);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

    }

    public void onClickGoToMaps(View v) {
        Intent intentMaps = new Intent(ProfileActivity.this, MapsFinal.class);
        Bundle mapParameters = new Bundle();
        mapParameters.putInt("intols", currentIntol);
        Log.i("i", "onClickGoToMaps" + currentIntol);
        intentMaps.putExtras(mapParameters);
        startActivity(intentMaps);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        currentIntol = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

