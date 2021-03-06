package com.ucd.user.weatherfitness;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // Opens Google Maps activity and allows user to search for new location and save.
    // Save button returns lat and long of searched location to main
    private GoogleMap mMap;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates map fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onSearch(View view) throws IOException {
        // Searches for location typed in text field using geocoder class
        EditText location_tf = findViewById(R.id.address);
        String location = location_tf.getText().toString();

        // If text box is not empty then search using geocoder and zoom camera on location
        if (!TextUtils.isEmpty(location)) {
            Geocoder geo = new Geocoder(this);
            List<Address> addressList = geo.getFromLocationName(location, 1);
            Address address = addressList.get(0);
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title("Marker on Search"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
        }
        else {
             Toast.makeText(getApplicationContext(), "Please select location", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSave(View view) throws IOException {
        // Returns the Searched location coordinates back to main
        EditText location_tf = findViewById(R.id.address);
        String location = location_tf.getText().toString();
        // If text box is not empty return coordinates to main
        if (!TextUtils.isEmpty(location)) {

            Geocoder geo = new Geocoder(this);
            List<Address> addressList = geo.getFromLocationName(location, 1);
            Address address = addressList.get(0);
            String lat = Double.toString(address.getLatitude());
            String lng = Double.toString(address.getLongitude());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("lat", lat);
            resultIntent.putExtra("lng", lng);
            resultIntent.putExtra("location",address);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        // If no location entered, ask user if they wish to use current location instead
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("You didn`t select any location. Current location will be used")
                    .setMessage("Are you sure?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            String lat = Double.toString(gps.getLatitude());
                            String lng = Double.toString(gps.getLongitude());
                            Geocoder geo = new Geocoder(getApplicationContext());
                            List<Address> addressList = null;
                            try {
                                addressList = geo.getFromLocation(latitude,longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Address current = addressList.get(0);
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("lat", lat);
                            resultIntent.putExtra("lng", lng);
                            resultIntent.putExtra("location",current);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })                        //Do nothing on no
                    .show();
        }}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // When map is ready, zoom in on current location using GPS
        mMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in current location and move the camera
        gps = new GPSTracker(MapsActivity.this);
        if (gps.canGetLocation()) {
            double lat = gps.getLatitude();
            double lng = gps.getLongitude();
            LatLng current = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(current).title("Current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, zoomLevel));

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
        }
    }


}
