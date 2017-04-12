
/**
 * Created by anoop on 22/8/15.
 */

package com.zeladder.zeladder;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FindVenueFragment extends Fragment {

    MapView mapView;
    GoogleMap map;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    ImageView cricket;
    ImageView badminton;
    ImageView squash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.find_venue, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        //get the image view for on click listners
        cricket = (ImageView) v.findViewById(R.id.cricket);
        badminton = (ImageView) v.findViewById(R.id.badminton);
        squash = (ImageView) v.findViewById(R.id.squash);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Updates the location and zoom of the MapView
      //  CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
       // map.animateCamera(cameraUpdate);

        // cuatom initialization for map functionality


        // Opening the sharedPreferences object
        sharedPreferences = getActivity().getSharedPreferences("location", 0);

        // Getting number of locations already stored
        locationCount = sharedPreferences.getInt("locationCount", 0);


        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");
        // custom functionality added here for map

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                locationCount++;

                // Drawing marker on the map
                drawMarker(point);

                //** Opening the editor object to write data to sharedPreferences *//*
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Storing the latitude for the i-th location
                editor.putString("lat"+ Integer.toString((locationCount-1)), Double.toString(point.latitude));

                // Storing the longitude for the i-th location
                editor.putString("lng"+ Integer.toString((locationCount-1)), Double.toString(point.longitude));

                // Storing the count of locations or marker count
                editor.putInt("locationCount", locationCount);

                //** Storing the zoom level to the shared preferences *//*
                editor.putString("zoom", Float.toString(map.getCameraPosition().zoom));

                //** Saving the values stored in the shared preferences *//*
                editor.commit();

                Toast.makeText(getActivity(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

            }
        });


        // on map long click make it clear map
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                // Removing the marker and circle from the Google Map
                map.clear();

                // Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Clearing the editor
                editor.clear();

                // Committing the changes
                editor.commit();

                // Setting locationCount to zero
                locationCount = 0;

            }
        });

        // zoom current location

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                map.moveCamera(center);
                map.animateCamera(zoom);

            }
        });

        // image button on click listners
        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                LatLng point = new LatLng(12.917359, 77.631862);
                // Drawing marker on the map
                drawMarker(point);

                LatLng point2 = new LatLng(12.917401, 77.633021);
                // Drawing marker on the map
                drawMarker(point2);

            }
        });

        badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                LatLng point = new LatLng(12.914933, 77.633107);
                // Drawing marker on the map
                drawMarker(point);

                LatLng point2 = new LatLng(12.915016, 77.634823);
                // Drawing marker on the map
                drawMarker(point2);
            }
        });

        squash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                LatLng point = new LatLng(12.914933, 77.633107);
                // Drawing marker on the map
                drawMarker(point);

                LatLng point2 = new LatLng(12.917401, 77.633021);
                // Drawing marker on the map
                drawMarker(point2);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        map.addMarker(markerOptions);
    }


}
