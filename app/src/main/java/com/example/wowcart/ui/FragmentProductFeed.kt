package com.example.wowcart.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeed : Fragment() {

    private lateinit var binding: FragmentProductFeedBinding
    private val viewModel by viewModels<ProductViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductFeedBinding.inflate(inflater)
        super.onCreate(savedInstanceState)
        binding = FragmentProductFeedBinding.inflate(layoutInflater)
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
            bottomBarView.setOnClickListener {
                Toast.makeText(context, "Cart button clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun onItemFavorite(string: String, isFav: Boolean) {
        Toast.makeText(this.context, "isFav: $string", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            feedRecyclerView.adapter = adapter
            feedRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            viewModel.status.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Failed -> showException(result.exception)
                    is Result.Success -> adapter.submitList(result.data)
                }
            }
        }
    }

}

private fun showException(exception: Exception) {
    Log.d("Exception", "_____________")
    exception.printStackTrace()
    Log.d("Exception", "END : _____________")
}
