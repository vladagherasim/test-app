package com.example.wowcart.domain.useCases

import com.example.wowcart.R
import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.presentation.components.ItemMiddleText
import com.example.wowcart.presentation.components.ItemText
import com.example.wowcart.presentation.details.ItemImage
import com.example.wowcart.utils.Dimen
import com.example.wowcart.utils.DimenS
import com.example.wowcart.utils.Margin
import com.example.wowcart.utils.TextTypes
import com.example.wowcart.utils.common.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend fun getProductByID(id: Int): Flow<MutableList<Item>> {
        val product = repository.getItemById(id)
        return flowOf(mutableListOf<Item>().apply {
            add(
                ItemImage(tag = "image", product.mainImage)
            )
            add(
                ItemMiddleText(
                    tag = "middle texts",
                    product.name,
                    product.details,
                    product.price.toDouble()
                )
            )
            add(
                ItemText(
                    tag = "information text",
                    DimenS._14ssp,
                    R.font.open_sans_extra_bold,
                    R.color.title_text_color_07195c,
                    Margin.Only(top = Dimen._20sdp),
                    TextTypes.ResText(R.string.information)
                )
            )
            add(
                ItemText(
                    tag = "description text",
                    DimenS._10ssp,
                    R.font.open_sans_regular,
                    R.color.common_text_424a56,
                    Margin.Only(top = Dimen._10sdp),
                    TextTypes.StringText(product.details)
                )
            )
        })
    }

    fun getItemInFavorites(id: Int): Flow<Boolean> {
        return repository.getItemInFavorites(id)
    }
}