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
import com.example.wowcart.ui.viewModels.ProductFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favorites : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<ProductFavoritesViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite, this::onItemClick)


    //TODO: warning
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
            //TODO: use default LinearLayoutManager(requireContext()) instead
            //TODO: in onViewCreated callback you should use requireContext() instead of context
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

    private fun onItemFavorite(product: Product, isFav: Boolean) {
        when (isFav) {
            true -> viewModel.insert(product.id)
            false -> viewModel.delete(product.id)
        }
    }

    private fun onItemClick(id: Int) {
        val directions = FavoritesDirections.actionFavoritesToProductDetails(id)
        findNavController().navigate(directions)
    }

    //TODO: missing onDestroyView with binding = null. Check official google documentation about ViewBinding in fragments

}