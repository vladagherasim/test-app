package com.example.wowcart.presentation.components

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wowcart.databinding.ItemImageBinding
import com.example.wowcart.utils.common.Item
import com.example.wowcart.presentation.feed.Payloads

data class ItemImage(
    val tag: String,
    val imageString: String
) : Item {
    override fun areItemsTheSame(other: Any): Boolean {
        return other is ItemImage && tag == other.tag
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return other is ItemImage && imageString == other.imageString
    }

    override fun getChangePayload(other: Any): Any {
        return mutableListOf<Payloads>().apply {
            if (other is ItemImage) {
                if (other.imageString != imageString)
                    add(ImagePayloads.LinkChanged(other.imageString))
            }
        }
    }

}

class ImageViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemImage) {
        setImage(item.imageString)
    }

    fun setImage(image: String) {
        binding.itemImage.load(image) {
            crossfade(400)
        }
    }
}


sealed class ImagePayloads : Payloads {
    data class LinkChanged(val newLink: String) : ImagePayloads()
}