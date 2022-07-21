package com.example.wowcart.presentation.components

import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wowcart.databinding.ItemInformationTextBinding
import com.example.wowcart.utils.common.Item
import com.example.wowcart.utils.IMargin
import com.example.wowcart.utils.TextTypes
import com.example.wowcart.utils.setMargin
import com.example.wowcart.utils.setTextType
import com.flexeiprata.novalles.annotations.AutoBindViewHolder
import com.flexeiprata.novalles.annotations.Instruction
import com.flexeiprata.novalles.annotations.PrimaryTag
import com.flexeiprata.novalles.annotations.UIModel
import com.flexeiprata.novalles.interfaces.Instructor
import com.flexeiprata.novalles.interfaces.Novalles

@UIModel
data class ItemText(
    @PrimaryTag val tag: String,
    @DimenRes val size: Int,
    @FontRes val font: Int,
    @ColorRes val color: Int,
    val margin: IMargin,
    val text: TextTypes

) : Item {
    private val uiModelHelper = Novalles.provideUiInterfaceFor(ItemText::class)

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

@Instruction(ItemText::class)
@AutoBindViewHolder(TextHolder::class)
class InformationInstructor : Instructor

class TextHolder(
    private val binding: ItemInformationTextBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context get() = itemView.context

    fun bind(item: ItemText) {
        setSize(item.size)
        setColor(item.color)
        setFont(item.font)
        setText(item.text)
        setMargin(item.margin)
    }

    fun setSize(@DimenRes size: Int) {
        val textSize = context.resources.getDimension(size)
        binding.informationText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun setColor(@ColorRes color: Int) {
        val colorValue = ContextCompat.getColor(context, color)
        binding.informationText.setTextColor(colorValue)
    }

    fun setText(text: TextTypes) {
        binding.informationText.setTextType(text)
    }

    fun setFont(fontFamily: Int) {
        val typeface = ResourcesCompat.getFont(context, fontFamily)
        binding.informationText.typeface = typeface
    }

    fun setMargin(margin: IMargin) {
        binding.informationText.setMargin(margin)
    }
}