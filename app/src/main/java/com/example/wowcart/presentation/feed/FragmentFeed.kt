package com.example.wowcart.presentation.feed

import android.animation.AnimatorInflater.loadStateListAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wowcart.R
import com.example.wowcart.databinding.FragmentProductFeedBinding
import com.example.wowcart.presentation.components.BaseFragment
import com.example.wowcart.utils.getNavOptions
import com.example.wowcart.utils.safeLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductFeed : BaseFragment<FragmentProductFeedBinding>(), ProductFragment {

    override val viewModel by viewModels<ProductFeedViewModel>()
    private val adapter = ProductAdapter(this::onItemFavorite, this::onItemClick, this)

    private lateinit var productsLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    private fun onItemFavorite(itemProduct: ItemProduct, isFav: Boolean) {
        when (isFav) {
            true -> viewModel.insert(itemProduct.id)
            false -> viewModel.delete(itemProduct.id)
        }
    }

    private fun onItemClick(id: Int) {
        val directions = ProductFeedDirections.actionProductFeedToProductDetails(id)
        findNavController().navigate(directions, getNavOptions())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            feedRecyclerView.adapter = adapter
            val context = requireContext()

            productsLayoutManager = if (listButton.isEnabled) {
                GridLayoutManager(context, 1)
            } else {
                GridLayoutManager(context, 2)
            }

            feedRecyclerView.layoutManager = productsLayoutManager
            viewModel.exceptions.observe(viewLifecycleOwner) { exception ->
                exception.printStackTrace()
            }

            bottomBarView.stateListAnimator = loadStateListAnimator(
                getContext(),
                R.animator.anim_button
            )

            listButton.isEnabled = false
            listButton.setOnClickListener {
                productsLayoutManager.spanCount = 1
                listButton.isEnabled = !listButton.isEnabled
                gridButton.isEnabled = !listButton.isEnabled
                adapter.notifyItemRangeChanged(0, feedRecyclerView.adapter!!.itemCount - 1)
            }
            gridButton.setOnClickListener {
                productsLayoutManager.spanCount = 2
                gridButton.isEnabled = !gridButton.isEnabled
                listButton.isEnabled = !gridButton.isEnabled
                adapter.notifyItemRangeChanged(0, feedRecyclerView.adapter!!.itemCount - 1)
            }
            toolbarHolder.viewBinding.rightButton.setOnClickListener {
                val directions = ProductFeedDirections.actionProductFeedToFavorites()
                findNavController().navigate(directions, getNavOptions())
            }
            bottomBarView.setOnClickListener {
                Toast.makeText(context, "Cart button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        lifecycleScope.safeLaunch(::onError) {
            getMovies()
        }
    }

    private suspend fun getMovies() {
        viewModel.getProducts().collectLatest { data ->
            adapter.submitData(data.map { it })
        }
    }

    private fun onError(error: Exception) {
        error.printStackTrace()
    }

    override fun getSpanCount() = productsLayoutManager.spanCount
    override fun getViewBinding() = FragmentProductFeedBinding.inflate(layoutInflater)
}
