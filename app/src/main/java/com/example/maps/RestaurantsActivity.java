package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RestaurantsActivity extends AppCompatActivity {

    JSONArray arrayRes = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        listView = findViewById(R.id.listView);

        DownloadTaskArray task = new DownloadTaskArray();
        ArrayList<String> resNames = new ArrayList<>();

        try {
            arrayRes = task.execute("https://greenwayiade.herokuapp.com/api/restaurantes").get();
            for (int i = 0; i < arrayRes.length(); i++) {
                resNames.add(arrayRes.getJSONObject(i).getString("name"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, resNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });
    }

        //Change Activities
        public void onClickGoToMaps (View v){
            Intent intentMaps = new Intent(RestaurantsActivity.this, MapsFinal.class);
            startActivity(intentMaps);
        }
}