package com.example.wowcart.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentProductDetailsBinding
import com.example.wowcart.presentation.components.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetails : BaseFragment<FragmentProductDetailsBinding>() {

    override val viewModel by viewModels<ProductDetailsViewModel>()
    private val args: ProductDetailsArgs by navArgs()
    private val adapter = DetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getItemById(args.id)
        viewModel.getItemInFavorites(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        viewModel.item.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
        binding.toolbarView.viewBinding.rightButton.setOnClickListener {
            binding.toolbarView.viewBinding.rightButton.apply {
                isSelected = !isSelected
                if (isSelected) {
                    viewModel.insert(args.id)
                } else {
                    viewModel.delete(args.id)
                }
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            binding.apply {
                toolbarView.rightIcon.isSelected = it
            }
        }
        viewModel.exceptions.observe(viewLifecycleOwner) {
            it.printStackTrace()
        }

        binding.toolbarView.viewBinding.middleButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun getViewBinding() = FragmentProductDetailsBinding.inflate(layoutInflater)

}


