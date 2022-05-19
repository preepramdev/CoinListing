package com.maxnimal.coin.listing.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.maxnimal.coin.listing.BuildConfig
import com.maxnimal.coin.listing.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("TAG", "onCreate BuildConfig.API_KEY: ${BuildConfig.API_KEY}")
    }
}