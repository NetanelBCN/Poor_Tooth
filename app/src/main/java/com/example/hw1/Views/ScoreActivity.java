package com.example.hw1.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.hw1.FragmentViews.MapFragment;
import com.example.hw1.FragmentViews.ScoreListFragment;
import com.example.hw1.Interfaces.PlayerCallback;
import com.example.hw1.Models.Player;
import com.example.hw1.R;
import com.example.hw1.Utilities.DataManager;
import com.example.hw1.Utilities.SharedPreferencesManager;
import com.google.android.gms.maps.model.LatLng;

public class ScoreActivity extends AppCompatActivity {

    private FrameLayout SCORE_FL_list;
    private FrameLayout SCORE_FL_map;
    private MapFragment mapFragment;
    private ScoreListFragment listFragment;
    public final double DEFAULT_LAT = 31.771959;
    public final double DEFAULT_LON = 35.217018;
    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        mapFragment = new MapFragment();
        listFragment = new ScoreListFragment();
        getIntents();
        listFragment.updateData(currentPlayer);
        listFragment.save();
        listFragment.getAdapter().setPlayerCallback((player, position) -> mapFragment.focusOnLocation(new LatLng(player.getLat(), player.getLon())));
        getSupportFragmentManager().beginTransaction().add(R.id.SCORE_FL_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.SCORE_FL_map, mapFragment).commit();
    }

    private void getIntents() {
        int currentScore = getIntent().getIntExtra("score", 0);
        double lat = getIntent().getDoubleExtra("lat", DEFAULT_LAT);
        double lon = getIntent().getDoubleExtra("lon", DEFAULT_LON);
        currentPlayer = new Player().setScore(currentScore).setLat(lat).setLon(lon);
    }

    private void findViews() {
        SCORE_FL_list = findViewById(R.id.SCORE_FL_list);
        SCORE_FL_map = findViewById(R.id.SCORE_FL_map);
    }
}