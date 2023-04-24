package com.example.weatherapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AlphaApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        Log.d("TAG", "initialized")
    }

}