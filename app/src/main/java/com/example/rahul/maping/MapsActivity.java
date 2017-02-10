package com.example.rahul.maping;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Premission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                List<Address> addresses = null;
                try {
                    addresses = new Geocoder(MapsActivity.this).getFromLocation(latLng.latitude, latLng.longitude, 1);
                    displaylocationinfo(addresses);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public  void displaylocationinfo(List<Address> addresses){

        Address position = addresses.get(0);
        LatLng latLng = new LatLng(position.getLatitude(),position.getLongitude());
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,5);
        mMap.moveCamera(cameraUpdate);
        String address = position.getAddressLine(0);
        String snippet =position.getCountryName();
        if(marker != null)
            marker.remove();
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(position.getLatitude(),position.getLongitude())).title(position.getLocality()).snippet(snippet + "\n" + address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // mMap.addMarker(new MarkerOptions().position(new LatLng(position.getLatitude(),position.getLongitude())).title(position.getLocality()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.a)));



        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.sd,null);
                TextView textView = (TextView) view.findViewById(R.id.textView);
                textView.setText(marker.getTitle());
                TextView snipet = (TextView) view.findViewById(R.id.snippet);
                snipet.setText(marker.getSnippet());

                return view;
            }
        });






////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


     //   gotoLocation(lat,lon,15);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

//    public void gotoLocation(double lat,double lon,float zoom){
//
//
//
//    }
//
//    public void gotoLocality(View view) throws IOException {
//
//
//
//
////        List<Address> addresses = new Geocoder(this).getFromLocationName(editText.getText().toString(),1);
////        displaylocationinfo(addresses);
//
//
////        Toast toast = Toast.makeText(this,addresses.get(0).toString(),Toast.LENGTH_LONG);
////        toast.show();
//
//       // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//    }


//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String msg = marker.getTitle() + " (" +
//                        marker.getPosition().latitude + ", " +
//                        marker.getPosition().longitude + ")";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker marker) {
//
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                LatLng latLng = marker.getPosition();
//                List<Address> addresses=null;
//                try {
//                    addresses = new Geocoder(getApplicationContext()).getFromLocation(latLng.latitude,latLng.longitude,1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Address position = addresses.get(0);
//                marker.setTitle(position.getLocality());
//                marker.setSnippet(position.getCountryName());
//                marker.showInfoWindow();
//            }
//        });



    }

