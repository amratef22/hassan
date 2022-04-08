package com.abdo.eduapp;

import android.app.Application;

import com.abdo.eduapp.local.MyRoomDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyRoomDatabase.initRoom(this);
    }
}
