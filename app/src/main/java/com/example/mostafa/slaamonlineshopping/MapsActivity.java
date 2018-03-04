package com.example.mostafa.slaamonlineshopping;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText adressText;
    LocationManager locationManager;
    myLocationListener locationListener;
    Button getLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        */

        adressText = (EditText) findViewById(R.id.ET_loc);
        getLoc = (Button) findViewById(R.id.Show);
        locationListener = new myLocationListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locationListener);

        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "NOT ALLOWED TO ACESS CURRENT LOcation!!! ", Toast.LENGTH_SHORT).show();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button=(Button)findViewById(R.id.B_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adressText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "send your location", Toast.LENGTH_SHORT).show();

                }
                else {
                    Date currentTime = Calendar.getInstance().getTime();
                    Toast.makeText(getApplicationContext(), currentTime.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    myDataBase dataBase = new myDataBase(getApplicationContext());
                    dataBase.add_order(intent.getStringExtra("products"),currentTime.toString(),
                            intent.getStringExtra("ID"),intent.getStringExtra("total"),
                            adressText.getText().toString());
                    intent=new Intent(MapsActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
      */
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                final List<Address> addressList;
                Location loc = null;
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              }catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(), "NOT ALLOWED TO ACESS CURRENT LOcation!!! ", Toast.LENGTH_SHORT).show();

                }
                if(loc!=null)
                {
                    LatLng myPosition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try {

                        addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            String adress="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                adress+=addressList.get(0).getAddressLine(i)+" , ";
                            mMap.addMarker(new MarkerOptions().position(myPosition)
                                    .title("my location").snippet(adress)).setDraggable(true);
                            adressText.setText(adress);
                        }
                    }catch (IOException e)
                    {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("my location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                }else
                {
                    Toast.makeText(getApplicationContext(), "Wait Ylaaa", Toast.LENGTH_SHORT).show();

                }
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        Geocoder coder=new Geocoder(getApplicationContext());
                        List<Address>addresslist;
                        try {
                            addresslist=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                            if(!addresslist.isEmpty())
                            {
                                String adress="";
                                for(int i=0;i<=addresslist.get(0).getMaxAddressLineIndex();i++)
                                    adress+=addresslist.get(0).getAddressLine(i)+" , ";

                                adressText.setText(adress);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "no address for this location", Toast.LENGTH_SHORT).show();
                                adressText.getText().clear();
                            }
                        }catch (IOException e)
                        {
                            Toast.makeText(getApplicationContext(), "can't get the address ,check the network", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {

                    }
                });
          }
      });
    }
}
