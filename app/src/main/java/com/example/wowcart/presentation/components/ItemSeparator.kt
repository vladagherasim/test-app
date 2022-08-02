package com.example.wowcart.presentation.components

import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wowcart.databinding.ItemSeparatorBinding
import com.example.wowcart.utils.IMargin
import com.example.wowcart.utils.TextTypes
import com.example.wowcart.utils.common.Item
import com.example.wowcart.utils.setMargin
import com.example.wowcart.utils.setTextType
import com.flexeiprata.novalles.annotations.AutoBindViewHolder
import com.flexeiprata.novalles.annotations.Instruction
import com.flexeiprata.novalles.annotations.PrimaryTag
import com.flexeiprata.novalles.annotations.UIModel
import com.flexeiprata.novalles.interfaces.Instructor
import com.flexeiprata.novalles.interfaces.Novalles

@UIModel
data class ItemSeparator(
    @PrimaryTag val tag: String,
    val text: TextTypes,
    @DimenRes val textSize: Int,
    @ColorRes val textColor: Int,
    @FontRes val textFont: Int,
    val margin: IMargin,
) : Item {
    private val uiModelHelper = Novalles.provideUiInterfaceFor(ItemSeparator::class)

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

@Instruction(ItemSeparator::class)
@AutoBindViewHolder(SeparatorHolder::class)
class SeparatorInstructor : Instructor

class SeparatorHolder(
    private val binding: ItemSeparatorBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context get() = itemView.context

    fun bind(item: ItemSeparator) {
        setSize(item.textSize)
        setColor(item.textColor)
        setFont(item.textFont)
        setText(item.text)
        setMargin(item.margin)
    }

    fun setSize(@DimenRes size: Int) {
        val textSize = context.resources.getDimension(size)
        binding.separatorText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun setColor(@ColorRes color: Int) {
        val colorValue = ContextCompat.getColor(context, color)
        binding.separatorText.setTextColor(colorValue)
    }

    fun setText(text: TextTypes) {
        binding.separatorText.setTextType(text)
    }

    fun setFont(fontFamily: Int) {
        val typeface = ResourcesCompat.getFont(context, fontFamily)
        binding.separatorText.typeface = typeface
    }

    fun setMargin(margin: IMargin) {
        binding.itemSeparator.setMargin(margin)
    }
}