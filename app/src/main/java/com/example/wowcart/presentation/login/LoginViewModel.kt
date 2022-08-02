package com.example.wowcart.presentation.login

import android.content.ClipData.Item
import androidx.lifecycle.ViewModel
import com.example.wowcart.R
import com.example.wowcart.presentation.components.ItemButton
import com.example.wowcart.presentation.components.ItemSeparator
import com.example.wowcart.presentation.components.ItemText
import com.example.wowcart.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel : ViewModel() {

    val itemList = mutableListOf<Item>()

    private fun initItems() {
        val items = (
            ItemText(
                tag = "welcomeText",
                text = TextTypes.ResText(R.string.welcome),
                color = R.color.title_text_color_07195c,
                size = DimenS._28ssp,
                margin = Margin.Only(top = Dimen._160sdp, left = Dimen._12sdp),
                font = R.font.open_sans_extra_bold
            ),
            ItemText(
                tag = "loginText",
                text = TextTypes.ResText(R.string.login_text),
                color = R.color.common_text_424a56,
                size = DimenS._14ssp,
                margin = Margin.Only(top = Dimen._10sdp, left = Dimen._12sdp),
                font = R.font.open_sans_regular
            ),
            ItemButton(
                tag = "loginWithGoogleButton",
                text = TextTypes.ResText(R.string.google_login),
                textColor = R.color.title_text_color_07195c,
                textSize = DimenS._11ssp,
                textFont = R.font.open_sans_regular,
                margin = Margin.Only(top = Dimen._25sdp, left = Dimen._12sdp, right = Dimen._12sdp),
                background = R.drawable.shape_gray_buttons
            ),
            ItemButton(
                tag = "loginWithFacebookButton" ,
                text = TextTypes.ResText(R.string.facebook_login),
                textColor = R.color.title_text_color_07195c,
                textSize = DimenS._11ssp,
                textFont = R.font.open_sans_regular,
                margin = Margin.Only(top = Dimen._25sdp, left = Dimen._12sdp, right = Dimen._12sdp),
                background = R.drawable.shape_gray_buttons
            ),
            ItemSeparator(
                tag = "loginScreenSeparator",
                textFont = R.font.open_sans_regular,
                textSize = DimenS._11ssp,
                margin = Margin.Only(left = Dimen._12sdp, right = Dimen._12sdp, top = Dimen._19sdp),
                textColor = R.color.common_text_424a56,
                text = TextTypes.ResText(R.string.or)
            ),
            ItemButton(
                tag = "loginAsGuestButton" ,
                text = TextTypes.ResText(R.string.continue_as_guest),
                textColor = R.color.title_text_color_07195c,
                textSize = DimenS._11ssp,
                textFont = R.font.open_sans_regular,
                margin = Margin.Only(top = Dimen._25sdp, left = Dimen._12sdp, right = Dimen._12sdp),
                background = R.drawable.shape_gray_buttons
            )
        )

    }

}