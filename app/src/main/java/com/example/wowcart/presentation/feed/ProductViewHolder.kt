package com.example.wowcart.presentation.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemProductFeedBinding
import com.example.wowcart.databinding.ItemProductFeedGridBinding

abstract class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ItemProduct)
    abstract fun setProductImage(image: String)
    abstract fun setProductPrice(price: Double)
    abstract fun setProductDescription(description: String)
    abstract fun setProductTitle(title: String)
    abstract fun setProductFavoriteStatus(isFavorite: Boolean)
    abstract fun setOnClickListeners(item: ItemProduct)
}

class ProductListViewHolder(
    private val binding: ItemProductFeedBinding,
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : ProductViewHolder(binding.root) {
    private val context = itemView.context

    override fun bind(item: ItemProduct) {
        setProductImage(item.image)
        setProductTitle(item.title)
        setProductDescription(item.description)
        setProductPrice(item.price)
        setProductFavoriteStatus(item.isFavorite)
    }

    override fun setProductImage(image: String) {
        binding.productImage.load(image)
    }

    override fun setProductPrice(price: Double) {
        binding.priceLarge.text = price.toString()
        binding.priceSmall.text = context.getString(R.string.price_holder, price.toString())
    }

    override fun setProductDescription(description: String) {
        binding.productDescription.text = description
    }

    override fun setProductTitle(title: String) {
        binding.productTitle.text = title
    }

    override fun setProductFavoriteStatus(isFavorite: Boolean) {
        binding.addToFavoritesButton.setImageResource(
            when (isFavorite) {
                true -> (R.drawable.ic_favorites_selected_feed)
                false -> (R.drawable.ic_favorites)
            }
        )
        binding.addToFavoritesButton.isSelected = isFavorite
    }

    override fun setOnClickListeners(item: ItemProduct) {
        binding.addToFavoritesButton.setOnClickListener {
            favoriteListener(item, !it.isSelected)
            binding.addToFavoritesButton.isSelected = !it.isSelected
        }
        binding.productInFeedContainer.setOnClickListener {
            itemClickListener(item.id)
        }
    }
}

class ProductGridViewHolder(
    private val binding: ItemProductFeedGridBinding,
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : ProductViewHolder(binding.root) {
    private val context = itemView.context

    override fun bind(item: ItemProduct) {
        setProductImage(item.image)
        setProductTitle(item.title)
        setProductDescription(item.description)
        setProductPrice(item.price)
        setProductFavoriteStatus(item.isFavorite)
    }

    override fun setProductImage(image: String) {
        binding.productImage.load(image)
    }

    override fun setProductPrice(price: Double) {
        binding.priceLarge.text = price.toString()
        binding.priceSmall.text = context.getString(R.string.price_holder, price.toString())
    }

    override fun setProductDescription(description: String) {
        binding.productDescription.text = description
    }

    override fun setProductTitle(title: String) {
        binding.productTitle.text = title
    }

    override fun setProductFavoriteStatus(isFavorite: Boolean) {
        binding.addToFavoritesButton.setImageResource(
            when (isFavorite) {
                true -> (R.drawable.ic_favorites_selected_feed)
                false -> (R.drawable.ic_favorites)
            }
        )
        binding.addToFavoritesButton.isSelected = isFavorite
    }

    override fun setOnClickListeners(item: ItemProduct) {
        binding.addToFavoritesButton.setOnClickListener {
            favoriteListener(item, !it.isSelected)
            binding.addToFavoritesButton.isSelected = !it.isSelected
        }
        binding.productInFeedContainer.setOnClickListener {
            itemClickListener(item.id)
        }
    }
}