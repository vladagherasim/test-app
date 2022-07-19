package com.example.wowcart.ui.feed
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemProductFeedBinding
import com.example.wowcart.ui.Item
import com.example.wowcart.ui.ItemDiffCallback


class ProductAdapter(
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit
) : PagingDataAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {
    private val itemProduct: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (viewType == itemProduct) {
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
        return if (getItem(position) is ItemProduct) itemProduct
        else throw IllegalArgumentException("No such type")

    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
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

class ItemViewHolder(
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

data class ItemProduct(
    val id: Int,
    val image: String,
    val title: String,
    val description: String,
    val price: Double,
    val isFavorite: Boolean
) : Item {
    override fun areItemsTheSame(other: Any): Boolean {
        return other is ItemProduct && id == other.id
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return other is ItemProduct
                && this.title == other.title
                && this.description == other.description
                && this.price == other.price
                && this.image == other.image
                && this.isFavorite == other.isFavorite
    }

    override fun getChangePayload(other: Any): MutableList<Payloads> {
        return mutableListOf<Payloads>().apply {
            if (other is ItemProduct) {
                if (other.title != title) {
                    add(ProductPayloads.TitleChanged(other.title))
                }
                if (other.image != image) {
                    add(ProductPayloads.ImageChanged(other.image))
                }
                if (other.price != price) {
                    add(ProductPayloads.PriceChanged(other.price))
                }
                if (other.description != description) {
                    add(ProductPayloads.DescriptionChanged(other.description))
                }
                if (other.isFavorite != isFavorite) {
                    add(ProductPayloads.FavoriteStatusChanged(other.isFavorite))
                }
            }
        }
    }
}

interface Payloads

sealed class ProductPayloads : Payloads {
    data class TitleChanged(val newTitle: String) : ProductPayloads()
    data class DescriptionChanged(val newDescription: String) : ProductPayloads()
    data class PriceChanged(val newPrice: Double) : ProductPayloads()
    data class ImageChanged(val newImage: String) : ProductPayloads()
    data class FavoriteStatusChanged(val newFavoriteStatus: Boolean) : ProductPayloads()
}