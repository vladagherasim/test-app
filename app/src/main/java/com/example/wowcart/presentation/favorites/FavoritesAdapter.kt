package com.example.wowcart.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemProductFeedBinding
import com.example.wowcart.utils.common.Item
import com.example.wowcart.presentation.ui.ItemDiffCallback
import com.example.wowcart.presentation.feed.ItemProduct
import com.example.wowcart.presentation.feed.ProductPayloads


class FavoritesAdapter(
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : ListAdapter<Item, FavoriteViewHolder>(ItemDiffCallback()) {
    private val itemProduct: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        if (viewType == itemProduct) {
            val binding = ItemProductFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FavoriteViewHolder(binding, favoriteListener, itemClickListener)
        } else throw IllegalArgumentException("No such type")
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is ItemProduct) itemProduct
        else throw IllegalArgumentException("No such type")

    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position) as ItemProduct? ?: return
        val myPayload = payloads.firstOrNull() as List<*>?
        if (myPayload.isNullOrEmpty()) {
            holder.bind(item)
        } else myPayload.forEach {
            when (it) {
                is ProductPayloads.TitleChanged -> holder.setProductTitle(it.newTitle)
                is ProductPayloads.DescriptionChanged -> holder.setProductDescription(it.newDescription)
                is ProductPayloads.ImageChanged -> holder.setProductImage(it.newImage)
                is ProductPayloads.PriceChanged -> holder.setProductPrice(it.newPrice)
                is ProductPayloads.FavoriteStatusChanged -> holder.setProductFavoriteStatus(it.newFavoriteStatus)
            }
        }
        holder.setOnClickListeners(item)
    }
}

class FavoriteViewHolder(
    private val binding: ItemProductFeedBinding,
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val context = itemView.context

    fun bind(item: ItemProduct) {
        setProductImage(item.image)
        setProductTitle(item.title)
        setProductDescription(item.description)
        setProductPrice(item.price)
        setProductFavoriteStatus(item.isFavorite)
    }

    fun setProductImage(image: String) {
        binding.productImage.load(image)
    }

    fun setProductPrice(price: Double) {
        binding.priceLarge.text = price.toString()
        binding.priceSmall.text = context.getString(R.string.price_holder, price.toString())
    }

    fun setProductDescription(description: String) {
        binding.productDescription.text = description
    }

    fun setProductTitle(title: String) {
        binding.productTitle.text = title
    }

    fun setProductFavoriteStatus(isFavorite: Boolean) {
        binding.addToFavoritesButton.setImageResource(
            when (isFavorite) {
                true -> (R.drawable.ic_favorites_selected_feed)
                false -> (R.drawable.ic_favorites)
            }
        )
        binding.addToFavoritesButton.isSelected = isFavorite
    }

    fun setOnClickListeners(item: ItemProduct) {
        binding.addToFavoritesButton.setOnClickListener {
            favoriteListener(item, !it.isSelected)
            binding.addToFavoritesButton.isSelected = !it.isSelected
        }
        binding.productInFeedContainer.setOnClickListener {
            itemClickListener(item.id)
        }
    }
}




