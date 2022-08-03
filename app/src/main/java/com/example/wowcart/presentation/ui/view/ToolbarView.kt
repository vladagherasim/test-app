package com.example.wowcart.presentation.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wowcart.R
import com.example.wowcart.databinding.ViewToolbarBinding

class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val viewBinding =
        ViewToolbarBinding.inflate(
            LayoutInflater.from(context), this,
            true
        )

    val rightIcon get() = viewBinding.rightButton

    init {
        attrs?.let {
            initAttributes(context, attrs)
        }
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val attr: TypedArray = getTypedArray(context, attrs, R.styleable.Toolbar)

        viewBinding.apply {
            leftButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarLeftImage,
                    0
                )
            )
            middleButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarCenterImage,
                    0
                )
            )
            rightButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarRightImage,
                    0
                )
            )

        }
    }

    private fun getTypedArray(
        context: Context,
        attributeSet: AttributeSet,
        attr: IntArray
    ): TypedArray {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0)
    }
}