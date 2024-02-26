package com.example.hw1.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hw1.Logic.GameManager;
import com.example.hw1.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    public static int ROWS = 6;
    public static int COLS = 5;
    public static int LIVES = 3;
    private GameManager gameManager;
    private ShapeableImageView[][] main_SIV;
    private AppCompatImageButton main_left_arrow;
    private AppCompatImageButton main_right_arrow;
    private ShapeableImageView main_bg;
    private final int DELAY = 1000;
    private int speed = 0;
    public static Handler handler = new Handler();
    public Vibrator vibrator;
    private ShapeableImageView[] main_IMG_hearts;
    private MaterialTextView main_MTV_score;
    private boolean isRunning = true;
    private boolean isSensorOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getIntents();
        loadBackground();

        gameManager = new GameManager().setLife(LIVES).setLocationMatrix(ROWS, COLS);
        main_left_arrow.setOnClickListener(view -> goLeft());
        main_right_arrow.setOnClickListener(view -> goRight());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        refreshUI();

    }

    private void loadBackground() {
        Glide.with(this)
                .load(R.drawable.mouth)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_bg);
    }

    private void getIntents() {
        speed = getIntent().getIntExtra("speed", speed);
        isSensorOn = getIntent().getBooleanExtra("isSensorOn", false);
    }

    private void startGameLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRunning)
                    return;
                refreshUI();
                gameManager.moveObjDown();
                refreshUI();
                handler.postDelayed(this, speed);
            }
        }, speed);
    }

    private void goRight() {
        gameManager.moveTooth("Right");
        refreshUI();
    }

    private void goLeft() {
        gameManager.moveTooth("Left");
        refreshUI();
    }

    private void refreshUI() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                if (gameManager.getLocationMatrix()[i][j].getType().equals("Candy"))
                    main_SIV[i][j].setImageResource(R.drawable.candy_sugar_svgrepo_com);
                else if (gameManager.getLocationMatrix()[i][j].getType().equals("Apple"))
                    main_SIV[i][j].setImageResource(R.drawable.green_apple);
                else if (gameManager.getLocationMatrix()[i][j].getType().equals("Tooth")) {
                    main_SIV[i][j].setImageResource(R.drawable.tooth_svgrepo_com);
                } else if (gameManager.getLocationMatrix()[i][j].getType().equals("Empty")) {
                    main_SIV[i][j].setImageResource(0);
                }
            }
            if (gameManager.isHitCandyFlag()) {
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getCrashCount()].setVisibility(View.INVISIBLE);
                vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(this, "Poor Tooth :(", Toast.LENGTH_SHORT).show();
                gameManager.setHitCandyFlag(false);
            }
            if (gameManager.isHitAppleFlag()) {
                main_MTV_score.setText("Score: " + gameManager.currentGameScore);
                gameManager.setHitAppleFlag(false);
            }

            if (gameManager.gameLost()){
                System.out.println("YOU LOST");// will help in part 2
                Intent intent=new Intent(this, ScoreActivity.class);
                startActivity(intent);
                finish();

                 }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        startGameLoop();
    }

    public void findViews() {
        main_SIV = new ShapeableImageView[][]{
                {findViewById(R.id.main_SIV_00), findViewById(R.id.main_SIV_01), findViewById(R.id.main_SIV_02), findViewById(R.id.main_SIV_03), findViewById(R.id.main_SIV_04)},
                {findViewById(R.id.main_SIV_10), findViewById(R.id.main_SIV_11), findViewById(R.id.main_SIV_12), findViewById(R.id.main_SIV_13), findViewById(R.id.main_SIV_14)},
                {findViewById(R.id.main_SIV_20), findViewById(R.id.main_SIV_21), findViewById(R.id.main_SIV_22), findViewById(R.id.main_SIV_23), findViewById(R.id.main_SIV_24)},
                {findViewById(R.id.main_SIV_30), findViewById(R.id.main_SIV_31), findViewById(R.id.main_SIV_32), findViewById(R.id.main_SIV_33), findViewById(R.id.main_SIV_34)},
                {findViewById(R.id.main_SIV_40), findViewById(R.id.main_SIV_41), findViewById(R.id.main_SIV_42), findViewById(R.id.main_SIV_43), findViewById(R.id.main_SIV_44)},
                {findViewById(R.id.main_SIV_50), findViewById(R.id.main_SIV_51), findViewById(R.id.main_SIV_52), findViewById(R.id.main_SIV_53), findViewById(R.id.main_SIV_54)}
        };
        main_left_arrow = findViewById(R.id.main_left_arrow);
        main_bg = findViewById(R.id.main_bg);
        main_MTV_score = findViewById(R.id.main_MTV_score);
        main_right_arrow = findViewById(R.id.main_right_arrow);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
    }
}