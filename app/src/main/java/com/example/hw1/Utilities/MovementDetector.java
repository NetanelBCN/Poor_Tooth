package com.example.hw1.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw1.Interfaces.MoveCallback;

public class MovementDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private long timeStamp = 0l;
    private MoveCallback moveCallback;
    private final double ACCELERATION = 2.0;

    public MovementDetector(Context context, MoveCallback moveCallback) {

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();

    }

    public void start() {

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }

    private void move(float x, float y) {
        String directionX;
        String speedY;
        directionX = (x > 0) ? "Left" : (x < 0) ? "Right" : "";
        speedY = (y > 0) ? "Slow" : (y < 0) ? "Fast" : "";

        if (System.currentTimeMillis() - timeStamp > 500) {
            timeStamp = System.currentTimeMillis();
            if (x > ACCELERATION || x < -ACCELERATION) {
                if (moveCallback != null)
                    moveCallback.moveX(directionX);
            }
            if (y > ACCELERATION || y < -ACCELERATION) {
                if (moveCallback != null)
                    moveCallback.moveY(speedY);
            }
        }
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                move(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}
