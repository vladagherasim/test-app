package com.example.wowcart.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wowcart.R
import com.example.wowcart.databinding.ActivityMainBinding
import com.example.wowcart.databinding.FragmentProductFeedBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentProductFeedBinding
    private val adapter : ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = FragmentProductFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.feedRecyclerView.adapter = adapter
    }
}