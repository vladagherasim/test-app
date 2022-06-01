package com.example.wowcart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemProductFeedBinding

private const val ITEM_PRODUCT: Int = 1

class ProductAdapter(
    private val favoriteListener: (String, Boolean) -> Unit
) : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (viewType == ITEM_PRODUCT) {
            val binding = ItemProductFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ItemViewHolder(binding, favoriteListener)
        } else throw IllegalArgumentException("No such type")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Product) ITEM_PRODUCT
        else throw IllegalArgumentException("No such type")

    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        //Create mapper Item.toUIModel(holder) || if ViewHolder is ...
        val item = getItem(position) as Product? ?: return

        if (payloads.isEmpty()) {
            holder.bind(item)
        } else payloads.forEach {
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

class ItemViewHolder(
    private val binding: ItemProductFeedBinding,
    private val favoriteListener: (String, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Product) {
        setProductImage(item.image)

    }

    fun setProductImage(image: String) {
        binding.productImage.setImageResource(R.drawable.ic_logo)
    }

    fun setProductPrice(price: Double) {
        binding.priceLarge.text = price.toString()
        binding.priceSmall.text = price.toString()
    }

    fun setProductDescription(description: String) {
        binding.productDescription.text = description
    }

    fun setProductTitle(title: String) {
        binding.productTitle.text = title
    }

    fun setProductFavoriteStatus(isFavorite: Boolean) {
        binding.addToFavoritesButton.isSelected = isFavorite
    }

    fun setOnClickListeners(item: Product) {
        binding.addToFavoritesButton.setOnClickListener {
            favoriteListener(item.title, !it.isSelected)
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

}