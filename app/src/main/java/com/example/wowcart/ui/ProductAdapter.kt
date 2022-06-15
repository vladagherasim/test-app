package com.example.wowcart.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemProductFeedBinding

private const val ITEM_PRODUCT: Int = 1

class ProductAdapter(
    private val favoriteListener: (Product, Boolean) -> Unit, private val itemClickListener: (Int) -> Unit
) : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (viewType == ITEM_PRODUCT) {
            val binding = ItemProductFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ItemViewHolder(binding, favoriteListener, itemClickListener)
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
        val item = getItem(position) as Product? ?: return
        val myPayload = payloads.firstOrNull() as List<Any>?
        if (position == 0) {
            Log.d("payloads", "total = $payloads")
            Log.d("payloads", myPayload.toString())
        }
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

class ItemViewHolder(
    private val binding: ItemProductFeedBinding,
    private val favoriteListener: (Product, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context = itemView.context

    fun bind(item: Product) {
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

    fun setOnClickListeners(item: Product) {
        binding.addToFavoritesButton.setOnClickListener {
            favoriteListener(item, !it.isSelected)
            binding.addToFavoritesButton.isSelected = !it.isSelected
        }
        binding.productInFeedContainer.setOnClickListener {
            itemClickListener(item.id)
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

    override fun getChangePayload(oldItem: Item, newItem: Item): Any? {
        return oldItem.getChangePayload(newItem)
    }

}