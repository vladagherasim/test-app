package com.example.wowcart.ui

data class Product(
    val id: Int,
    val image: String,
    val title: String,
    val description: String,
    val price: Double,
    val isFavorite: Boolean
) : Item {
    override fun areItemsTheSame(other: Any): Boolean {

        return other is Product && id == other.id
    }

    override fun areContentsTheSame(other: Any): Boolean {
        return other is Product
                && this.title == other.title
                && this.description == other.description
                && this.price == other.price
                && this.image == other.image
    }

    override fun getChangePayload(other: Any): MutableList<Payloads> {
        return mutableListOf<Payloads>().apply {
            if (other is Product) {
                if (other.title != title) {
                    add(ProductPayloads.TitleChanged(other.title))
                }
                if (other.image != image) {
                    add(ProductPayloads.ImageChanged(other.image))
                }
                if (other.price != price) {
                    add(ProductPayloads.PriceChanged(other.price))
                }
                if (other.description != description) {
                    add(ProductPayloads.DescriptionChanged(other.description))
                }
                if (other.isFavorite != isFavorite) {
                    add(ProductPayloads.FavoriteStatusChanged(other.isFavorite))
                }
            }
        }
    }
}