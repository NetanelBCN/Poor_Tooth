package com.example.hw1.FragmentViews;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw1.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap myGoogleMap;
    public final double DEFAULT_LAT = 31.771959;
    public final double DEFAULT_LON = 35.217018;
    private LatLng DEFAULT_LOC = new LatLng(DEFAULT_LAT, DEFAULT_LON);
    private MarkerOptions markerOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews();
        return view;
    }

    private void findViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MAPFRAG_MV_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.myGoogleMap = googleMap;
        markerOptions = new MarkerOptions();
        focusOnLocation(DEFAULT_LOC);
        myGoogleMap.addMarker(markerOptions);
    }

    public void focusOnLocation(LatLng location) {
        myGoogleMap.clear();
        markerOptions.position(location).title((getLocationString(location)));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        myGoogleMap.addMarker(markerOptions);
    }

    public String getLocationString(LatLng location) {
        Geocoder geoCoder = new Geocoder(requireContext()); // or getParentFragment().requireContext() depending on your setup
        String result = "Unknown location";

        try {
            List<Address> list = geoCoder.getFromLocation(location.latitude, location.longitude, 1);

            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                result = address.getLocality() + ", " + address.getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}