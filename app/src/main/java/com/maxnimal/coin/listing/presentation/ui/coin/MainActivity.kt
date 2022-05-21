package com.maxnimal.coin.listing.presentation.ui.coin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.maxnimal.coin.listing.BuildConfig
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.ActivityMainBinding
import com.maxnimal.coin.listing.presentation.extension.setStatusBar

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setStatusBar(R.color.status_bar, resources.getBoolean(R.bool.isDarkTheme))
    }
}