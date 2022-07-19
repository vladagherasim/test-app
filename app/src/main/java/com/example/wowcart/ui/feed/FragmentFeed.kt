package com.example.wowcart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.R
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.ui.feed.ItemProduct
import com.example.wowcart.ui.feed.ProductAdapter
import com.example.wowcart.ui.feed.ProductFeedViewModel
import com.example.wowcart.utils.safeLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductFeed : Fragment() {

    private var _binding: FragmentProductFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductFeedViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite, this::onItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductFeedBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        _binding = FragmentProductFeedBinding.inflate(layoutInflater)

        return binding.root
    }

    private fun onItemFavorite(itemProduct: ItemProduct, isFav: Boolean) {
        when (isFav) {
            true -> viewModel.insert(itemProduct.id)
            false -> viewModel.delete(itemProduct.id)
        }
    }

    private fun onItemClick(id: Int) {
        val directions = ProductFeedDirections.actionProductFeedToProductDetails(id)
        findNavController().navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            feedRecyclerView.adapter = adapter
            feedRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewModel.exceptions.observe(viewLifecycleOwner) { exception ->
                exception.printStackTrace()
            }
        }
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
    }

    private fun loadData() {
        lifecycleScope.safeLaunch(::onError) {
            getMovies()
        }
    }

    private suspend fun getMovies() {
        viewModel.getProducts().collectLatest {
            adapter.submitData(it.map { it })
        }
    }

    private fun onError(error: Exception) {
        error.printStackTrace()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}