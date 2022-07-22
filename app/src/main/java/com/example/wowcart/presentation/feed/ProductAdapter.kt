package com.example.wowcart.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import com.example.wowcart.databinding.ItemProductFeedBinding
import com.example.wowcart.databinding.ItemProductFeedGridBinding
import com.example.wowcart.presentation.ui.ItemDiffCallback
import com.example.wowcart.utils.common.Item


class ProductAdapter(
    private val favoriteListener: (ItemProduct, Boolean) -> Unit,
    private val itemClickListener: (Int) -> Unit,
    fragment: Fragment
) : PagingDataAdapter<Item, ProductViewHolder>(ItemDiffCallback()) {

    private val parentInterface: ProductFragment = fragment as ProductFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return if (viewType == 1) {
            val binding = ItemProductFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProductListViewHolder(binding, favoriteListener, itemClickListener)
        } else {
            val binding = ItemProductFeedGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProductGridViewHolder(binding, favoriteListener, itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun getItemViewType(position: Int): Int {
        return parentInterface.getSpanCount()

    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
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
interface ProductFragment {
    fun getSpanCount() : Int
}