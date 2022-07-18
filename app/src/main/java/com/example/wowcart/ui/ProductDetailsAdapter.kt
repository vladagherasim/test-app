package com.example.wowcart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.wowcart.R
import com.example.wowcart.databinding.ItemDescriptionBinding
import com.example.wowcart.databinding.ItemImageBinding
import com.example.wowcart.databinding.ItemInformationTextBinding
import com.example.wowcart.databinding.ItemMiddleTextBinding
import com.flexeiprata.novalles.annotations.AutoBindViewHolder
import com.flexeiprata.novalles.annotations.Instruction
import com.flexeiprata.novalles.annotations.PrimaryTag
import com.flexeiprata.novalles.annotations.UIModel
import com.flexeiprata.novalles.interfaces.Instructor
import com.flexeiprata.novalles.interfaces.Novalles
import com.flexeiprata.novalles.interfaces.UIModelHelper

class ProductDetailsAdapter : ListAdapter<Item, ViewHolder>(ItemDiffCallback()) {
    private val itemImage: Int = 1
    private val itemMiddleText: Int = 2
    private val itemInformation: Int = 3
    private val itemDescription: Int = 4
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
            itemDescription -> {
                val binding = ItemDescriptionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DescriptionHolder(binding)
            }
            itemInformation -> {
                val binding = ItemInformationTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return InformationHolder(binding)
            }
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemDescription -> itemDescription
            is ItemImage -> itemImage
            is ItemMiddleText -> itemMiddleText
            is ItemInformation -> itemInformation
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when (holder) {
            is DescriptionHolder -> {
                val item = getItem(position) as ItemDescription? ?: return
                val myPayload = payloads.firstOrNull() as List<*>?
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else myPayload.forEach {
                    when (it) {
                        is DescriptionPayloads.DescriptionChanged -> holder.setDescription(it.newDescription)
                    }
                }
            }
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
            is InformationHolder -> {
                val item = getItem(position) as ItemInformation? ?: return
                val myPayload = payloads.firstOrNull() as List<*>?
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else inspector.inspectPayloads(payloads, InformationInstructor(), holder)
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

class DescriptionHolder(
    private val binding: ItemDescriptionBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemDescription) {
        setDescription(item.description)
    }

    fun setDescription(description: String) {
        binding.detailedInfoText.text = description
    }
}

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

sealed class ImagePayloads : Payloads {
    data class LinkChanged(val newLink: String) : ImagePayloads()
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

@UIModel
data class ItemInformation(
    @PrimaryTag val tag: String,
    val size: Float,
    @FontRes val font: Int,
    @ColorRes val color: Int,
    val text: String

) : Item {
    private val uiModelHelper: UIModelHelper<Item> =
        Novalles.provideUiInterfaceForAs(ItemInformation::class)

    override fun areItemsTheSame(other: Any): Boolean {
        return uiModelHelper.areItemsTheSame(this, other)
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return uiModelHelper.areContentsTheSame(this, other)
    }

    override fun getChangePayload(other: Any): Any {
        return uiModelHelper.changePayloads(this, other)
    }

}

@Instruction(ItemInformation::class)
@AutoBindViewHolder(InformationHolder::class)
class InformationInstructor : Instructor

private val inspector = Novalles.provideInspectorFromInstructor(InformationInstructor::class)


class InformationHolder(
    private val binding: ItemInformationTextBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context = itemView.context

    fun bind(item: ItemInformation) {
        setSize(item.size)
        setColor(item.color)
        setFont(item.font)
        setText(item.text)
    }

    private fun setSize(size: Float) {
        binding.informationText.textSize = size
    }

    private fun setColor(color: Int) {
        binding.informationText.setTextColor(color)
    }

    private fun setText(text: String) {
        binding.informationText.text = text
    }

    private fun setFont(fontFamily: Int) {
        val typeface = getFont(context, fontFamily)
        binding.informationText.typeface = typeface
    }
}

data class ItemDescription(
    val tag: String,
    val description: String
) : Item {
    override fun areItemsTheSame(other: Any): Boolean {
        return other is ItemDescription && tag == other.tag
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return other is ItemDescription && other.description == other.description
    }

    override fun getChangePayload(other: Any): Any {
        return mutableListOf<Payloads>().apply {
            if (other is ItemDescription) {
                if (other.description != description) {
                    add(DescriptionPayloads.DescriptionChanged(other.description))
                }
            }
        }
    }

}

sealed class DescriptionPayloads : Payloads {
    data class DescriptionChanged(val newDescription: String) : DescriptionPayloads()
}