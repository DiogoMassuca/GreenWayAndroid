package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    private Spinner spinner1;
    JSONArray arrayIntols = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DownloadTaskArray task = new DownloadTaskArray();
        ArrayList<String> intolNames = new ArrayList<String>();
        try{
            arrayIntols = task.execute("https://greenwayiade.herokuapp.com/api/intols").get();
            for (int i = 0; i < arrayIntols.length(); i++){
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

    public void addItemsOnSpinner(List<String> intols){
        spinner1 = findViewById(R.id.spinner1);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, intols);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void onClickGoToMaps(View v){
        Intent intentMaps = new Intent(ProfileActivity.this, MapsFinal.class);
        startActivity(intentMaps);
    }
}