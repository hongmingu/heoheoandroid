package com.example.keepair.myapplication;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keepair.myapplication.loginhelper.ReferSharedPreference;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Permission;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Permissions;
import java.util.ArrayList;

/**
 * Created by Keepair on 2016-08-26.
 */
public class BlueFragment extends Fragment implements OnMapReadyCallback {

    public GoogleMap googleMap;
    private TextView mCoordinatesTextMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blue, container, false);
        MapView mapview = (MapView) view.findViewById(R.id.map_blue);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        mCoordinatesTextMap = (TextView) view.findViewById(R.id.tv_coordinates_map);

        return view;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        }
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                ReferSharedPreference preferenceCoordinates = new ReferSharedPreference(getContext());
                LatLng point = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
                MarkerOptions marker = new MarkerOptions()
                        .position(point).title("ok");
                preferenceCoordinates.put("Coor", "{ \"longitude\": \""+ googleMap.getMyLocation().getLatitude() +"\",     \"latitude\": \""+ googleMap.getMyLocation().getLongitude() +"\" } ");
                preferenceCoordinates.put("Lat", String.valueOf(googleMap.getMyLocation().getLatitude()));
                preferenceCoordinates.put("Lon", String.valueOf(googleMap.getMyLocation().getLongitude()));
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
                googleMap.addMarker(marker);
                String lat = preferenceCoordinates.getValue("Lat", "13");
                String lon = preferenceCoordinates.getValue("Lon", "15");
                mCoordinatesTextMap.setText(lat + "  , " + lon);
                return true;
            }
        });


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                ReferSharedPreference preferenceCoordinates = new ReferSharedPreference(getContext());
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("ok");
                preferenceCoordinates.put("Coor", "{ \"longitude\": \""+point.latitude +"\",     \"latitude\": \""+point.longitude+"\" } ");
                preferenceCoordinates.put("Lat", String.valueOf(point.latitude));
                preferenceCoordinates.put("Lon", String.valueOf(point.longitude));

                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
                googleMap.addMarker(marker);


                String lat = preferenceCoordinates.getValue("Lat", "13");
                String lon = preferenceCoordinates.getValue("Lon", "15");
                mCoordinatesTextMap.setText(lat + "  , " + lon);
            }
        });

    }

}
