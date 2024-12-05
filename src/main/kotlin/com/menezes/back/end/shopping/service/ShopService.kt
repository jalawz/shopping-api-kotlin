package com.menezes.back.end.shopping.service

import com.menezes.back.end.shopping.converter.DTOConverter
import com.menezes.back.end.shopping.exceptions.ResourceNotFoundException
import com.menezes.back.end.shopping.model.Shop
import com.menezes.back.end.shopping.repository.ShopRepository
import com.menezes.backend.client.dto.ItemDTO
import com.menezes.backend.client.dto.ShopDTO
import com.menezes.backend.client.dto.ShopReportDTO
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ShopService(
    private val shopRepository: ShopRepository,
    private val userService: UserService,
    private val productService: ProductService,
) {
    fun getAll(): List<ShopDTO> {
        return shopRepository.findAll()
            .map { shop -> DTOConverter.convert(shop) }
    }

    fun getByUserIdentifier(userIdentifier: String): List<ShopDTO> {
        return shopRepository.findAllByUserIdentifier(userIdentifier)
            .map { shop -> DTOConverter.convert(shop) }
    }

    fun getByDate(dto: ShopDTO): List<ShopDTO> {
        return shopRepository.findAllByDateGreaterThan(dto.date)
            .map { shop -> DTOConverter.convert(shop) }
    }

    fun findByProductId(productId: Long): Shop {
        return shopRepository.findById(productId).orElseThrow {
            ResourceNotFoundException("${Shop::class.java.simpleName} with id $productId not found")
        }
    }

    fun saveShop(dto: ShopDTO): ShopDTO? {
        val userByCpf = userService.getUserByCpf(dto.userIdentifier)

        if (!validateProducts(dto.items)) return null

        val shop =
            shopRepository.save(
                Shop.convert(dto).copy(
                    total = dto.items.map { it.price }.sum(),
                    date = LocalDateTime.now(),
                ),
            )
        return DTOConverter.convert(shop)
    }

    fun getShopsByFilter(
        initialDate: LocalDate,
        endDate: LocalDate?,
        minValue: Float?,
    ): List<ShopDTO> {
        val shops = shopRepository.getShopByFilters(initialDate, endDate, minValue)
        return shops.map { DTOConverter.convert(it) }
    }

    fun getReportByDate(
        initialDate: LocalDate,
        endDate: LocalDate,
    ): ShopReportDTO {
        return shopRepository.getReportByDate(initialDate, endDate)
    }

    private fun validateProducts(items: List<ItemDTO>): Boolean {
        items.forEach { itemDTO ->
            val product = productService.getProductByIdentifier(itemDTO.productIdentifier) ?: return false
            itemDTO.price = product.price
        }
        return true
    }
}
