package com.example.wowcart.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.R
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeed : Fragment() {

    private lateinit var binding: FragmentProductFeedBinding
    private val viewModel by viewModels<ProductViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductFeedBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        binding = FragmentProductFeedBinding.inflate(layoutInflater)
        binding.apply {
            listButton.isEnabled = false
            listButton.setOnClickListener {
                listButton.isEnabled = !listButton.isEnabled
                gridButton.isEnabled = !listButton.isEnabled
            }
            gridButton.setOnClickListener {
                gridButton.isEnabled = !gridButton.isEnabled
                listButton.isEnabled = !gridButton.isEnabled
            }
            toolbarHolder.viewBinding.rightButton.setOnClickListener {
                findNavController().navigate(R.id.action_productFeed_to_favorites)
            }
            bottomBarView.setOnClickListener {
                Toast.makeText(context, "Cart button clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun onItemFavorite(product: Product, isFav: Boolean) {
        when (isFav) {
            true -> viewModel.insert(product.id)
            false -> viewModel.delete(product.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            feedRecyclerView.adapter = adapter
            feedRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewModel.status.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
            viewModel.exceptions.observe(viewLifecycleOwner) {
                it.printStackTrace()
            }
        }
    }

}

fun showException(exception: Exception) {
    Log.d("Exception", "_____________")
    exception.printStackTrace()
    Log.d("Exception", "END : _____________")
}
