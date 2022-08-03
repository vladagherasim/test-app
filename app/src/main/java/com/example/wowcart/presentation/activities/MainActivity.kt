package com.example.wowcart.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.wowcart.R
import com.example.wowcart.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val account = GoogleSignIn.getLastSignedInAccount(this)
        navController = navHostFragment.navController
        val currentGraph = navController?.navInflater?.inflate(R.navigation.nav_graph)

        currentGraph?.setStartDestination(
            if (account != null) {
                R.id.productFeed
            } else {
                R.id.login
            }
        )
        currentGraph?.let {
            navController?.graph = it
        }

    }
}