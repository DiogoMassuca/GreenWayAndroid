package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.maps.databinding.ActivityMapsFinalBinding;
//import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MapsFinal extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ActivityMapsFinalBinding binding;
    MapView mapView;
    LocationManager locationManager;
    LocationListener locationListener;
    JSONArray arrayRes = null;
    int currentIntol = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        Log.i("i", ""+b);
        if(b != null)
            currentIntol = b.getInt("intols");


        // 1 - Criar o location Manager para ir buscar
        // a localização onde o nosso device está
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // 2 - Criar um location Listener para
        // detetar mudanças na nossa localização
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your location"));

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
        };

        // 3 - Detetar se o utilizador nos deu
        // permissões para obter a localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener
            );
        }
    }

    // 4 - Verificar se temos permissões ao executar a nossa Activity
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0,
                        locationListener
                );
            }
        }
    }


    // OnMapReadyCallBack Methods

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if (currentIntol != -1) {

            DownloadTaskArray task = new DownloadTaskArray();
            ArrayList<Double> resLat = new ArrayList<>();
            ArrayList<Double> resLongt = new ArrayList<>();

            try {
                Log.i("i", "" + currentIntol);
                arrayRes = task.execute("https://greenwayiade.herokuapp.com/api/restaurantes/not/" + currentIntol).get();
                for (int i = 0; i < arrayRes.length(); i++) {
                    resLat.add(arrayRes.getJSONObject(i).getDouble("restauranteLat"));
                    resLongt.add(arrayRes.getJSONObject(i).getDouble("restauranteLongt"));
                    LatLng resTempPos = new LatLng(resLat.get(i), resLongt.get(i));

                    //Marcardores nos restaurantes
                    mMap.addMarker(new MarkerOptions().position(resTempPos));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Change Activities
    public void onClickGoToProfile(View v){
        Intent intentProfile = new Intent(MapsFinal.this, ProfileActivity.class);
        startActivity(intentProfile);
    }
}






