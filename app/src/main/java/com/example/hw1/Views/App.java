package com.example.hw1.Views;

import android.app.Application;

import com.example.hw1.Utilities.SharedPreferencesManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
    }
}
