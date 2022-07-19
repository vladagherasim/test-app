package com.example.wowcart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentFavoritesBinding
import com.example.wowcart.ui.favorites.FavoritesAdapter
import com.example.wowcart.ui.favorites.ProductFavoritesViewModel
import com.example.wowcart.ui.feed.ItemProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favorites : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductFavoritesViewModel>()
    private val adapter = FavoritesAdapter(this::onItemFavorite, this::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            favoritesRecyclerView.adapter = adapter
            favoritesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())

            viewModel.favorites.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
            viewModel.exceptions.observe(viewLifecycleOwner) {
                it.printStackTrace()
            }
        }

        binding.favoritesToolbar.viewBinding.middleButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.favoritesCount.observe(viewLifecycleOwner) { result ->
            binding.favoritesCount.text = result.toString()
        }
        viewModel.exceptions.observe(viewLifecycleOwner) {
            it.printStackTrace()
        }
    }

    private fun onItemFavorite(itemProduct: ItemProduct, isFav: Boolean) {
        when (isFav) {
            true -> viewModel.insert(itemProduct.id)
            false -> viewModel.delete(itemProduct.id)
        }
    }

    private fun onItemClick(id: Int) {
        val directions = FavoritesDirections.actionFavoritesToProductDetails(id)
        findNavController().navigate(directions)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}