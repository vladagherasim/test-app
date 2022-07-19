package com.example.wowcart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentProductDetailsBinding
import com.example.wowcart.ui.details.DetailsAdapter
import com.example.wowcart.ui.details.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetails : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private val args: ProductDetailsArgs by navArgs()
    private val adapter = DetailsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        _binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


