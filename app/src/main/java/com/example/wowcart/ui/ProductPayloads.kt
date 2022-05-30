package com.example.wowcart.ui

interface Payloads

sealed class ProductPayloads : Payloads {
    data class TitleChanged(val newTitle: String) : ProductPayloads()
    data class DescriptionChanged(val newDescription: String) : ProductPayloads()
    data class PriceChanged(val newPrice: Double) : ProductPayloads()
    data class ImageChanged(val newImage: String) : ProductPayloads()
    data class FavoriteStatusChanged(val newFavoriteStatus: Boolean) : ProductPayloads()
}


