package com.example.wowcart.presentation.components

import androidx.recyclerview.widget.RecyclerView
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemMiddleTextBinding
import com.example.wowcart.utils.common.Item
import com.example.wowcart.presentation.feed.Payloads


class MiddleTextHolder(
    private val binding: ItemMiddleTextBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context = itemView.context

    fun bind(item: ItemMiddleText) {
        setPrice(item.price)
        setShortDescription(item.shortDescription)
        setTitle(item.title)
    }

    fun setPrice(price: Double) {

        if (binding.priceBig.text.isNotBlank()) {
            binding.priceBig.animate()
                .translationY(-100f)
                .alpha(0.2f)
                .setDuration(0)
                .start()

            binding.priceBig.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(259)
                .start()
        }

        binding.priceBig.text = price.toString()
        binding.priceSmaller.text = context.getString(R.string.price_holder, price.toString())
    }

    fun setShortDescription(description: String) {
        binding.shortDescription.text = description
    }

    fun setTitle(title: String) {
        binding.titleText.text = title
    }
}

data class ItemMiddleText(
    val tag: String,
    val title: String,
    val shortDescription: String,
    val price: Double
) : Item {
    override fun areItemsTheSame(other: Any): Boolean {
        return other is ItemMiddleText && other.tag == tag
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return other is ItemMiddleText
                && other.title == title
                && other.shortDescription == shortDescription
                && other.price == price
    }

    override fun getChangePayload(other: Any): Any {
        return mutableListOf<Payloads>().apply {
            if (other is ItemMiddleText) {
                if (other.title != title) {
                    add(MiddleTextPayloads.TitleChanged(other.title))
                }
                if (other.price != price) {
                    add(MiddleTextPayloads.PriceChanged(other.price))
                }
                if (other.shortDescription != shortDescription) {
                    add(MiddleTextPayloads.ShortDescriptionChanged(other.shortDescription))
                }
            }
        }
    }

}

sealed class MiddleTextPayloads : Payloads {
    data class PriceChanged(val newPrice: Double) : MiddleTextPayloads()
    data class TitleChanged(val newTitle: String) : MiddleTextPayloads()
    data class ShortDescriptionChanged(val newDescription: String) : MiddleTextPayloads()
}