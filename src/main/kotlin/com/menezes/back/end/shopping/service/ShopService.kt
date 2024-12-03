package com.menezes.back.end.shopping.service

import com.menezes.back.end.shopping.dto.ShopDTO
import com.menezes.back.end.shopping.dto.ShopReportDTO
import com.menezes.back.end.shopping.exceptions.ResourceNotFoundException
import com.menezes.back.end.shopping.model.Shop
import com.menezes.back.end.shopping.repository.ShopRepository
import org.springframework.cglib.core.Local
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ShopService(
    private val shopRepository: ShopRepository
) {

    fun getAll(): List<ShopDTO> {
        return shopRepository.findAll()
            .map { shop -> ShopDTO.convert(shop) }
    }

    fun getByUserIdentifier(userIdentifier: String): List<ShopDTO> {
        return shopRepository.findAllByUserIdentifier(userIdentifier)
            .map { shop -> ShopDTO.convert(shop) }
    }

    fun getByDate(dto: ShopDTO): List<ShopDTO> {
        return shopRepository.findAllByDateGreaterThan(dto.date)
            .map { shop -> ShopDTO.convert(shop) }
    }

    fun findByProductId(productId: Long): Shop {
        return shopRepository.findById(productId).orElseThrow {
            ResourceNotFoundException("${Shop::class.java.simpleName} with id $productId not found")
        }
    }

    fun saveShop(dto: ShopDTO): ShopDTO {
        val shop = shopRepository.save(
            Shop.convert(dto).copy(
                total = dto.items.map { it.price }.sum(),
                date = LocalDateTime.now()
            )
        )
        return ShopDTO.convert(shop)
    }

    fun getShopsByFilter(
        initialDate: LocalDate,
        endDate: LocalDate?,
        minValue: Float?
    ): List<ShopDTO> {
        val shops = shopRepository.getShopByFilters(initialDate, endDate, minValue)
        return shops.map { ShopDTO.convert(it) }
    }

    fun getReportByDate(
        initialDate: LocalDate,
        endDate: LocalDate
    ): ShopReportDTO {
        return shopRepository.getReportByDate(initialDate, endDate)
    }
}