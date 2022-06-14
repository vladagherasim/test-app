package com.example.wowcart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentFavoritesBinding
import com.example.wowcart.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favorites : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<ProductViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            favoritesRecyclerView.adapter = adapter
            favoritesRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewModel.status.observe(viewLifecycleOwner) { result ->
                /*when (result) {
                    is DataResult.Failed -> showException(result.exception)
                    is DataResult.Success -> adapter.submitList(result.data)
                }*/
            }
        }
    }

    private fun onItemFavorite(product: Product, isFav: Boolean) {

    }

}