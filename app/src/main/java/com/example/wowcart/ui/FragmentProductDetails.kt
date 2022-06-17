package com.example.wowcart.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.FragmentProductDetailsBinding
import com.example.wowcart.ui.viewModels.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetails : Fragment() {
    //TODO: lack of new lines
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private val args: ProductDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    } //TODO: warning

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        Log.d("ID", "id is $id")
        viewModel.getItemById(id)
        viewModel.item.observe(viewLifecycleOwner) { result ->
            binding.apply {
                titleText.text = result.name
                shortDescription.text = result.details
                priceBig.text = result.price.toString()
                priceSmaller.text = getString(R.string.price_holder, result.price.toString())
                detailedInfoText.text = result.details
                itemImage.load(result.mainImage)

            }
        }
        viewModel.exceptions.observe(viewLifecycleOwner) {
            it.printStackTrace()
        }

        binding.toolbarView.viewBinding.middleButton.setOnClickListener {
            findNavController().popBackStack()
        }

        //TODO: useless function
        binding.toolbarView.viewBinding.rightButton.setOnClickListener {

        }

    }

    //TODO: missing onDestroyView with binding = null. Check official google documentation about ViewBinding in fragments

}