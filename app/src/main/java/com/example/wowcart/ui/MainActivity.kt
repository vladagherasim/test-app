package com.example.wowcart.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.domain.ProductViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentProductFeedBinding
    private val viewModel: ProductViewModel by viewModels()
    private val adapter = ProductAdapter(this::onItemFavorite)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = FragmentProductFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.feedRecyclerView.adapter = adapter
        viewModel.getData()
        binding.lifecycleOwner = this


    }


    private fun onItemFavorite(string: String, isFav: Boolean) {
        Toast.makeText(this, "isFav: $string", Toast.LENGTH_SHORT).show()
    }
}