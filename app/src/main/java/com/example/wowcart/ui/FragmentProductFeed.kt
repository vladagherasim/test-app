package com.example.wowcart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.wowcart.R
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.domain.ProductViewModel


class ProductFeed : Fragment() {
    private val viewModel: ProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductFeedBinding.inflate(inflater)
        return binding.root

    }

}