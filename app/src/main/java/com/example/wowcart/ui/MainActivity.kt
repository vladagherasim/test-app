package com.example.wowcart.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wowcart.R
import com.example.wowcart.databinding.ActivityMainBinding

//TODO: add .idea recommended files to gitignore. Check https://stackoverflow.com/questions/16736856/what-should-be-in-my-gitignore-for-an-android-studio-project
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //TODO: do not put new line before {, it is java coding style
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}