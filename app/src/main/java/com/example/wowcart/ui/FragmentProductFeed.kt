package com.example.wowcart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentProductFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeed : Fragment() {

    private lateinit var binding: FragmentProductFeedBinding
    private val viewModel: ProductViewModel by viewModels()
    private val adapter = ProductAdapter(this::onItemFavorite)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductFeedBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        binding = FragmentProductFeedBinding.inflate(layoutInflater)
        binding.apply {
            listButton.setOnClickListener{
                listButton.isEnabled = !listButton.isEnabled
                gridButton.isEnabled = !listButton.isEnabled
            }
            gridButton.setOnClickListener{
                gridButton.isEnabled = !gridButton.isEnabled
                listButton.isEnabled = !gridButton.isEnabled
            }
            feedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            feedRecyclerView.adapter = adapter
            val result = viewModel.getProducts()

           /* cartAccessButton.setOnClickListener{
                Toast.makeText(context, "Cart button clicked", Toast.LENGTH_SHORT)
            }*/
        }
        return binding.root


    }
    private fun onItemFavorite(string: String, isFav: Boolean) {
        Toast.makeText(this.context, "isFav: $string", Toast.LENGTH_SHORT).show()
    }

}