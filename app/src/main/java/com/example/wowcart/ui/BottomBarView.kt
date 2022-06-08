package com.example.wowcart.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wowcart.R
import com.example.wowcart.databinding.ViewBottomBarBinding

class BottomBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding =
        ViewBottomBarBinding.inflate(
            LayoutInflater.from(context), this,
            true
        )

    init {
        attrs?.let {
            initAttributes(context, attrs)
        }
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val attr: TypedArray = getTypedArray(context, attrs, R.styleable.BottomBar) ?: return

        viewBinding.apply {
            cartButtonInBottomBar.setImageResource(
                attr.getResourceId(
                    R.styleable.BottomBar_cartIcon,
                    R.drawable.ic_user
                )
            )
            middleText.text = attr.getString(R.styleable.BottomBar_middleText)
            cartText.text = attr.getString(R.styleable.BottomBar_itemsNumber)

        }
    }

    private fun getTypedArray(
        context: Context,
        attributeSet: AttributeSet,
        attr: IntArray
    ): TypedArray? {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0)
    }
}