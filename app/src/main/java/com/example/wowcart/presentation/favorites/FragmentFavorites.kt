package com.example.wowcart.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentFavoritesBinding
import com.example.wowcart.presentation.components.BaseFragment
import com.example.wowcart.presentation.feed.ItemProduct
import com.example.wowcart.utils.getNavOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favorites : BaseFragment<FragmentFavoritesBinding>() {
    override val viewModel by viewModels<ProductFavoritesViewModel>()
    private val adapter = FavoritesAdapter(this::onItemFavorite, this::onItemClick)

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
        findNavController().navigate(directions, getNavOptions())
    }

    override fun getViewBinding() = FragmentFavoritesBinding.inflate(layoutInflater)

}