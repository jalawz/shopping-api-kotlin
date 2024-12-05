package com.menezes.back.end.shopping.converter

import com.menezes.back.end.shopping.model.Item
import com.menezes.back.end.shopping.model.Shop
import com.menezes.backend.client.dto.ItemDTO
import com.menezes.backend.client.dto.ShopDTO

object DTOConverter {
    private fun convert(item: Item): ItemDTO {
        return ItemDTO(
            productIdentifier = item.productIdentifier,
            price = item.price,
        )
    }

    fun convert(shop: Shop) =
        ShopDTO(
            userIdentifier = shop.userIdentifier,
            total = shop.total,
            date = shop.date,
            items = shop.items.map { DTOConverter.convert(it) },
        )
}
