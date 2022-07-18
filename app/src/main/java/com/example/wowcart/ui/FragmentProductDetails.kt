package com.example.wowcart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wowcart.databinding.FragmentProductDetailsBinding
import com.example.wowcart.ui.viewModels.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetails : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private val args: ProductDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        _binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id

        /*binding.informationText.isVisible = false
        binding.toolbarView.rightIcon.isVisible = false

        viewModel.getItemById(id)
        viewModel.item.observe(viewLifecycleOwner) { result ->
            binding.apply {
                titleText.text = result.name
                shortDescription.text = result.details
                priceBig.text = result.price.toString()
                priceSmaller.text = getString(R.string.price_holder, result.price.toString())
                detailedInfoText.text = result.details
                itemImage.load(result.mainImage)
                informationText.isVisible = true
                toolbarView.rightIcon.isVisible = true
            }*/

        viewModel.getItemInFavorites(id)
        binding.toolbarView.viewBinding.rightButton.setOnClickListener {
            binding.toolbarView.viewBinding.rightButton.apply {
                isSelected = !isSelected
                if (isSelected) {
                    viewModel.insert(id)
                } else {
                    viewModel.delete(id)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


