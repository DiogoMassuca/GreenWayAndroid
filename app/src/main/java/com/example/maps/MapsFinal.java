package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.maps.databinding.ActivityMapsFinalBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapsFinal extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ActivityMapsFinalBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MapView mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onClickGoToProfile(View v){
        Intent intentProfile = new Intent(MapsFinal.this, ProfileActivity.class);
        startActivity(intentProfile);
    }
}






