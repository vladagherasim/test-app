package com.example.wowcart.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wowcart.R
import com.example.wowcart.databinding.ViewToolbarBinding

//TODO: you should not create different toolbars for different screens with such light changes
//TODO: make it more configurable instead
class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
     val viewBinding =
        ViewToolbarBinding.inflate(
            LayoutInflater.from(context), this,
            true
        )

    init {
        attrs?.let {
            initAttributes(context, attrs)
        }
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val attr: TypedArray = getTypedArray(context, attrs, R.styleable.Toolbar) ?: return

        viewBinding.apply {
            leftButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarLeftImage,
                    R.drawable.ic_user
                )
            )
            middleButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarCenterImage,
                    R.drawable.ic_logo
                )
            )
            rightButton.setImageResource(
                attr.getResourceId(
                    R.styleable.Toolbar_toolbarRightImage,
                    R.drawable.ic_favorites
                )
            )

        }
    }

    //TODO: warning
    private fun getTypedArray(
        context: Context,
        attributeSet: AttributeSet,
        attr: IntArray
    ): TypedArray? {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0)
    }
}