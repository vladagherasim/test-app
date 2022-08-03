package com.example.wowcart.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wowcart.databinding.ItemButtonBinding
import com.example.wowcart.databinding.ItemInformationTextBinding
import com.example.wowcart.databinding.ItemSeparatorBinding
import com.example.wowcart.presentation.components.*
import com.example.wowcart.presentation.ui.ItemDiffCallback
import com.example.wowcart.utils.common.Item
import com.flexeiprata.novalles.interfaces.Novalles

class LoginAdapter(val processAction: (LoginAction) -> Unit) : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {
    private val itemText = 1
    private val itemButton = 2
    private val itemSeparator = 3

    private val buttonInspector = Novalles.provideInspectorFromInstructor(ButtonInstructor::class)
    private val textInspector = Novalles.provideInspectorFromInstructor(TextInstructor::class)
    private val separatorInspector = Novalles.provideInspectorFromInstructor(SeparatorInstructor::class)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            itemButton -> {
                val binding = ItemButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ButtonHolder(binding, processAction)
            }
            itemText -> {
                val binding = ItemInformationTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TextHolder(binding)
            }
            itemSeparator -> {
                val binding = ItemSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SeparatorHolder(binding)
            }
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemText -> itemText
            is ItemButton -> itemButton
            is ItemSeparator -> itemSeparator
            else -> throw IllegalArgumentException("No such type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        when (holder) {
            is TextHolder -> {
                val item = getItem(position) as ItemText? ?: return
                val myPayload = (payloads.firstOrNull() as List<*>?)?.mapNotNull { it as? String }
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else {
                    textInspector.inspectPayloads(myPayload, TextInstructor(), holder)
                }
            }
            is SeparatorHolder -> {
                val item = getItem(position) as ItemSeparator? ?: return
                val myPayload = (payloads.firstOrNull() as List<*>?)?.mapNotNull { it as? String }
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else {
                    buttonInspector.inspectPayloads(myPayload, ButtonInstructor(), holder)
                }
            }
            is ButtonHolder -> {
                val item = getItem(position) as ItemButton? ?: return
                val myPayload = (payloads.firstOrNull() as List<*>?)?.mapNotNull { it as? String }
                if (myPayload.isNullOrEmpty()) {
                    holder.bind(item)
                } else {
                    separatorInspector.inspectPayloads(myPayload, SeparatorInstructor(), holder)
                }
            }
        }
    }

    sealed interface LoginAction {
        data class ButtonClicked(val tag: String): LoginAction
    }

}

