package com.example.wowcart.presentation.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.wowcart.databinding.ItemImageBinding
import com.example.wowcart.databinding.ItemInformationTextBinding
import com.example.wowcart.databinding.ItemMiddleTextBinding
import com.example.wowcart.presentation.components.*
import com.example.wowcart.utils.common.Item
import com.example.wowcart.presentation.ui.ItemDiffCallback
import com.flexeiprata.novalles.interfaces.Novalles

class DetailsAdapter : ListAdapter<Item, ViewHolder>(ItemDiffCallback()) {
    private val itemImage: Int = 1
    private val itemMiddleText: Int = 2
    private val itemText: Int = 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            itemImage -> {
                val binding = ItemImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ImageViewHolder(binding)
            }
            itemMiddleText -> {
                val binding = ItemMiddleTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MiddleTextHolder(binding)
            }

            itemText -> {
                val binding = ItemInformationTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TextHolder(binding)
            }
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemImage -> itemImage
            is ItemMiddleText -> itemMiddleText
            is ItemText -> itemText
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        Log.d("Payloads", "$holder $payloads")
        when (holder) {
            is MiddleTextHolder -> {
                val item = getItem(position) as ItemMiddleText? ?: return
                val myPayload = payloads.firstOrNull() as List<*>?
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else myPayload.forEach {
                    when (it) {
                        is MiddleTextPayloads.PriceChanged -> holder.setPrice(it.newPrice)
                        is MiddleTextPayloads.TitleChanged -> holder.setTitle(it.newTitle)
                        is MiddleTextPayloads.ShortDescriptionChanged -> holder.setShortDescription(
                            it.newDescription
                        )
                    }
                }
            }
            is ImageViewHolder -> {
                val item = getItem(position) as ItemImage? ?: return
                val myPayload = payloads.firstOrNull() as List<*>?
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else myPayload.forEach {
                    when (it) {
                        is ImagePayloads.LinkChanged -> holder.setImage(it.newLink)
                    }
                }
            }
            is TextHolder -> {
                Log.d("Payload", "pp start")
                val item = getItem(position) as ItemText? ?: return
                val myPayload = (payloads.firstOrNull() as List<*>?)?.mapNotNull { it as? String }
                Log.d("Payload", "pp = $myPayload")
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else {
                    inspector.inspectPayloads(myPayload, TextInstructor(), holder)
                }
            }

        }
    }
}

private val inspector = Novalles.provideInspectorFromInstructor(TextInstructor::class)

