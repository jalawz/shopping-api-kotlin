package com.menezes.back.end.shopping.controller

import com.menezes.back.end.shopping.model.Shop
import com.menezes.back.end.shopping.service.ShopService
import com.menezes.backend.client.dto.ShopDTO
import com.menezes.backend.client.dto.ShopReportDTO
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDate

@RestController
@RequestMapping("/shopping")
class ShopController(
    private val shopService: ShopService,
) {
    @GetMapping
    fun getShops(): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getAll()
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shopByUser/{userId}")
    fun getShops(
        @PathVariable userId: String,
    ): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getByUserIdentifier(userId)
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shopByDate")
    fun getShops(
        @RequestBody dto: ShopDTO,
    ): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getByDate(dto)
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: Long,
    ): ResponseEntity<Shop> {
        val shop = shopService.findByProductId(id)
        return ResponseEntity.ok(shop)
    }

    @PostMapping
    fun createShop(
        @Valid @RequestBody dto: ShopDTO,
    ): ResponseEntity<ShopDTO> {
        val shop = shopService.saveShop(dto)
        val location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(shop.userIdentifier)
                .toUri()
        return ResponseEntity.created(location).body(shop)
    }

    @GetMapping("/search")
    fun getShopsByFilter(
        @RequestParam(name = "initialDate", required = true)
        @DateTimeFormat(pattern = "dd/MM/yyyy") initialDate: LocalDate,
        @RequestParam(name = "endDate", required = false)
        @DateTimeFormat(pattern = "dd/MM/yyyy") endDate: LocalDate?,
        @RequestParam(name = "minValue", required = false) minValue: Float?,
    ): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getShopsByFilter(initialDate, endDate, minValue)
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/report")
    fun getReportByDate(
        @RequestParam(name = "initialDate", required = true)
        @DateTimeFormat(pattern = "dd/MM/yyyy") initialDate: LocalDate,
        @RequestParam(name = "endDate", required = true)
        @DateTimeFormat(pattern = "dd/MM/yyyy") endDate: LocalDate,
    ): ResponseEntity<ShopReportDTO> {
        val report = shopService.getReportByDate(initialDate, endDate)
        return ResponseEntity.ok(report)
    }
}
