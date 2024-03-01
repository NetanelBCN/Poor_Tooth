package com.example.hw1.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class StartingActivity extends AppCompatActivity {

    private MaterialButton STARTING_BTN_slow;
    private MaterialButton STARTING_BTN_fast;
    private MaterialButton STARTING_BTN_mode;
    private MaterialTextView STARTING_MTV_mode;
    private ShapeableImageView start_bg;
    private final int SLOW = 1000;
    private final int FAST = 500;
    private boolean isSensorOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        findViews();
        loadBackground();
        enableButtons();
    }

    private void enableButtons() {
        STARTING_BTN_slow.setOnClickListener((view) -> {
            startGameActivity(SLOW);
        });
        STARTING_BTN_fast.setOnClickListener((view) -> {
            startGameActivity(FAST);
        });
        STARTING_BTN_mode.setOnClickListener((view) -> {
            isSensorOn = !isSensorOn;
            if (isSensorOn)
                STARTING_MTV_mode.setText(R.string.SENSOR_ON);
            else
                STARTING_MTV_mode.setText(R.string.SENSOR_OFF);
        });
    }

    private void startGameActivity(int speed) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("speed", speed);
        intent.putExtra("isSensorOn", isSensorOn);
        startActivity(intent);
        finish();
    }

    private void loadBackground() {
        Glide.with(this)
                .load(R.drawable.lips)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(start_bg);
    }

    private void findViews() {
        STARTING_BTN_slow = findViewById(R.id.STARTING_BTN_slow);
        STARTING_BTN_fast = findViewById(R.id.STARTING_BTN_fast);
        STARTING_MTV_mode = findViewById(R.id.STARTING_MTV_mode);
        STARTING_BTN_mode = findViewById(R.id.STARTING_BTN_mode);
        start_bg = findViewById(R.id.start_bg);
    }
}