package com.example.wowcart.presentation.login

import androidx.lifecycle.ViewModel
import com.example.wowcart.R
import com.example.wowcart.domain.useCases.PostLoginUseCase
import com.example.wowcart.domain.useCases.RegisterUserUseCase
import com.example.wowcart.presentation.components.ItemButton
import com.example.wowcart.presentation.components.ItemSeparator
import com.example.wowcart.presentation.components.ItemText
import com.example.wowcart.utils.Dimen
import com.example.wowcart.utils.DimenS
import com.example.wowcart.utils.Margin
import com.example.wowcart.utils.TextTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    val itemList = mutableListOf<com.example.wowcart.utils.common.Item>()

    init {
        initItems()
    }

    private fun initItems() {

        val welcomeText = ItemText(
            tag = "welcomeText",
            text = TextTypes.ResText(R.string.welcome),
            color = R.color.title_text_color_07195c,
            size = DimenS._28ssp,
            margin = Margin.Only(top = Dimen._84sdp, left = Dimen._12sdp),
            font = R.font.open_sans_extra_bold,
        )
        val loginText = ItemText(
            tag = "loginText",
            text = TextTypes.ResText(R.string.login_text),
            color = R.color.common_text_424a56,
            size = DimenS._14ssp,
            margin = Margin.Only(top = Dimen._10sdp, left = Dimen._12sdp),
            font = R.font.open_sans_regular
        )
        val loginGoogle = ItemButton(
            tag = "loginWithGoogleButton",
            text = TextTypes.ResText(R.string.google_login),
            textColor = R.color.title_text_color_07195c,
            textSize = DimenS._11ssp,
            textFont = R.font.open_sans_bold,
            margin = Margin.Only(top = Dimen._19sdp, left = Dimen._12sdp, right = Dimen._12sdp),
            background = R.drawable.shape_gray_buttons,
            iconStart = R.drawable.ic_google
        )
        val loginFacebook = ItemButton(
            tag = "loginWithFacebookButton",
            text = TextTypes.ResText(R.string.facebook_login),
            textColor = R.color.title_text_color_07195c,
            textSize = DimenS._11ssp,
            textFont = R.font.open_sans_bold,
            margin = Margin.Only(top = Dimen._13sdp, left = Dimen._12sdp, right = Dimen._12sdp),
            background = R.drawable.shape_gray_buttons,
            iconStart = R.drawable.ic_facebok
        )
        val separator = ItemSeparator(
            tag = "loginScreenSeparator",
            textFont = R.font.open_sans_regular,
            textSize = DimenS._11ssp,
            margin = Margin.Only(left = Dimen._12sdp, right = Dimen._12sdp, top = Dimen._19sdp),
            textColor = R.color.common_text_424a56,
            text = TextTypes.ResText(R.string.or)
        )
        val loginGuest = ItemButton(
            tag = "loginAsGuestButton",
            text = TextTypes.ResText(R.string.continue_as_guest),
            textColor = R.color.title_text_color_07195c,
            textSize = DimenS._11ssp,
            textFont = R.font.open_sans_bold,
            margin = Margin.Only(top = Dimen._19sdp, left = Dimen._12sdp, right = Dimen._12sdp),
            background = R.drawable.shape_gray_buttons,
        )
        itemList.add(welcomeText)
        itemList.add(loginText)
        itemList.add(loginGoogle)
        itemList.add(loginFacebook)
        itemList.add(separator)
        itemList.add(loginGuest)
    }

    fun postLogin(email: String, password: String){
        postLoginUseCase(email, password)
    }


}