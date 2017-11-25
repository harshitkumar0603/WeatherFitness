package com.ucd.user.weatherfitness;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ucd.user.weatherfitness.model.AddEventToCal;
import com.ucd.user.weatherfitness.model.FetchWeatherTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



////comment

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView score_id = (TextView)findViewById(R.id.score_ID);
        FetchWeatherTask weatherTask = new FetchWeatherTask(score_id);
        weatherTask.execute("7778677");
// branch test
        // Added a comment - Kumar
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        int longitude;
                        int lat;
                        Toast toast = Toast.makeText(MainActivity.this,"location added",Toast.LENGTH_SHORT);
                        toast.show();
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            toast = Toast.makeText(MainActivity.this,"location null",Toast.LENGTH_SHORT);
                            toast.show();
                            // Logic to handle location object
                        }
                    }
                });


        final Button btnAdd = (Button)findViewById(R.id.btnAddEvent);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventToCal objEvent = new AddEventToCal(MainActivity.this);
                try {
                    objEvent.AddEvent(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }





}