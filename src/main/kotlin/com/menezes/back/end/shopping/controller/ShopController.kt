package com.menezes.back.end.shopping.controller

import com.menezes.back.end.shopping.dto.ShopDTO
import com.menezes.back.end.shopping.model.Shop
import com.menezes.back.end.shopping.service.ShopService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/shopping")
class ShopController(
    private val shopService: ShopService
) {

    @GetMapping
    fun getShops(): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getAll()
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shopByUser/{userId}")
    fun getShops(@PathVariable userId: String): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getByUserIdentifier(userId)
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shopByDate")
    fun getShops(@RequestBody dto: ShopDTO): ResponseEntity<List<ShopDTO>> {
        val shops = shopService.getByDate(dto)
        return ResponseEntity.ok(shops)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Shop> {
        val shop = shopService.findByProductId(id)
        return ResponseEntity.ok(shop)
    }

    @PostMapping
    fun createShop(@Valid @RequestBody dto: ShopDTO): ResponseEntity<ShopDTO> {
        val shop = shopService.saveShop(dto)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(shop.userIdentifier)
            .toUri()
        return ResponseEntity.created(location).body(shop)
    }
}