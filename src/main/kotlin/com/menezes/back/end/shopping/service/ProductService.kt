package com.menezes.back.end.shopping.service

import com.menezes.back.end.shopping.exceptions.ResourceNotFoundException
import com.menezes.backend.client.dto.ProductDTO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ProductService {
    private val productApiURL = "http://localhost:8081"

    fun getProductByIdentifier(productIdentifier: String): ProductDTO? {
        return runCatching {
            val webClient =
                WebClient.builder()
                    .baseUrl(productApiURL)
                    .build()

            webClient.get()
                .uri("/products/$productIdentifier")
                .retrieve()
                .bodyToMono(ProductDTO::class.java).block()
        }.onFailure {
            throw ResourceNotFoundException("Product not found")
        }.getOrThrow()
    }
}
