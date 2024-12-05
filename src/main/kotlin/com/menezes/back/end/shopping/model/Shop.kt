package com.menezes.back.end.shopping.model

import com.menezes.backend.client.dto.ItemDTO
import com.menezes.backend.client.dto.ShopDTO
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime

@Entity(name = "shop")
data class Shop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userIdentifier: String,
    val total: Float,
    val date: LocalDateTime,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "item",
        joinColumns = [
            JoinColumn(name = "shop_id"),
        ],
    )
    val items: List<Item>,
) {
    companion object {
        fun convert(dto: ShopDTO) =
            Shop(
                userIdentifier = dto.userIdentifier,
                total = dto.total,
                date = dto.date,
                items = dto.items.map { Item.convert(it) },
            )
    }
}

@Embeddable
data class Item(
    val productIdentifier: String,
    val price: Float,
) {
    companion object {
        fun convert(dto: ItemDTO) =
            Item(
                productIdentifier = dto.productIdentifier,
                price = dto.price,
            )
    }
}
