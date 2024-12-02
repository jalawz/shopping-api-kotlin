package com.menezes.back.end.shopping.dto

import com.menezes.back.end.shopping.model.Item
import com.menezes.back.end.shopping.model.Shop
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ShopDTO(
    @NotBlank
    val userIdentifier: String,
    @NotNull
    val total: Float,
    @NotNull
    val date: LocalDateTime = LocalDateTime.now(),
    @NotNull
    val items: List<ItemDTO> = emptyList()
) {
    companion object {
        fun convert(shop: Shop) = ShopDTO(
            userIdentifier = shop.userIdentifier,
            total = shop.total,
            date = shop.date,
            items = shop.items.map { item -> ItemDTO.convert(item) }
        )
    }
}

data class ItemDTO(
    @NotBlank
    val productIdentifier: String,
    @NotNull
    val price: Float
) {
    companion object {
        fun convert(item: Item) = ItemDTO(
            productIdentifier = item.productIdentifier,
            price = item.price
        )
    }
}