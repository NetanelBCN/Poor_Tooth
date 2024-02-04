package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hw1.Logic.GameManager;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    public static int ROWS = 6;
    public static int COLS = 3;
    public static int LIVES = 3;
    private GameManager gameManager;
    private ShapeableImageView[][] main_SIV;
    private AppCompatImageButton main_left_arrow;
    private AppCompatImageButton main_right_arrow;
    private ShapeableImageView main_bg;
    private final int DELAY = 1000;
    public static Handler handler = new Handler();
    public Vibrator vibrator;
    private ShapeableImageView[] main_IMG_hearts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Glide.with(this)
                .load(R.drawable.mouth)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_bg);
        gameManager = new GameManager().setLife(LIVES).setLocationMatrix(ROWS, COLS);
        main_left_arrow.setOnClickListener(view -> goLeft());
        main_right_arrow.setOnClickListener(view -> goRight());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        refreshUI();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshUI();
                gameManager.moveObjDown();
                refreshUI();
                handler.postDelayed(this, DELAY);

            }
        }, DELAY);
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

                if (gameManager.getLocationMatrix()[i][j].getType().equals("Candy")) {
                    main_SIV[i][j].setImageResource(R.drawable.candy_sugar_svgrepo_com);
                } else if (gameManager.getLocationMatrix()[i][j].getType().equals("Tooth")) {
                    main_SIV[i][j].setImageResource(R.drawable.tooth_svgrepo_com);
                } else if (gameManager.getLocationMatrix()[i][j].getType().equals("Empty")) {
                    main_SIV[i][j].setImageResource(0);
                }
            }
            if (gameManager.isHitFlag()) {
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getCrashCount()].setVisibility(View.INVISIBLE);
                vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
                Toast.makeText(this, "Poor Tooth :(", Toast.LENGTH_SHORT).show();
                gameManager.setHitFlag(false);
            }

//            if (gameManager.gameLost()) System.out.println("YOU LOST");// will help in part 2
        }
    }

    public void findViews() {
        main_SIV = new ShapeableImageView[][]{
                {findViewById(R.id.main_SIV_00), findViewById(R.id.main_SIV_01), findViewById(R.id.main_SIV_02)},
                {findViewById(R.id.main_SIV_10), findViewById(R.id.main_SIV_11), findViewById(R.id.main_SIV_12)},
                {findViewById(R.id.main_SIV_20), findViewById(R.id.main_SIV_21), findViewById(R.id.main_SIV_22)},
                {findViewById(R.id.main_SIV_30), findViewById(R.id.main_SIV_31), findViewById(R.id.main_SIV_32)},
                {findViewById(R.id.main_SIV_40), findViewById(R.id.main_SIV_41), findViewById(R.id.main_SIV_42)},
                {findViewById(R.id.main_SIV_50), findViewById(R.id.main_SIV_51), findViewById(R.id.main_SIV_52)}
        };
        main_left_arrow = findViewById(R.id.main_left_arrow);
        main_bg=findViewById(R.id.main_bg);
        main_right_arrow = findViewById(R.id.main_right_arrow);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
    }
}