package com.example.hw1.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hw1.FragmentViews.MapFragment;
import com.example.hw1.FragmentViews.ScoreListFragment;
import com.example.hw1.R;

public class ScoreActivity extends AppCompatActivity {

    private FrameLayout SCORE_FL_list;
    private FrameLayout SCORE_FL_map;
    private MapFragment mapFragment;
    private ScoreListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        mapFragment=new MapFragment();
        listFragment=new ScoreListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.SCORE_FL_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.SCORE_FL_map, mapFragment).commit();

    }

    private void findViews() {
        SCORE_FL_list = findViewById(R.id.SCORE_FL_list);
        SCORE_FL_map = findViewById(R.id.SCORE_FL_map);
    }
}