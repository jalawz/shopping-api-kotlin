package com.menezes.back.end.shopping.service

import com.menezes.back.end.shopping.exceptions.ResourceNotFoundException
import com.menezes.backend.client.dto.UserDTO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class UserService {
    private val userApiURL = "http://localhost:8080"

    fun getUserByCpf(cpf: String): UserDTO? {
        return runCatching {
            val webClient =
                WebClient.builder()
                    .baseUrl(userApiURL)
                    .build()

            webClient.get()
                .uri("/users/$cpf/cpf")
                .retrieve()
                .bodyToMono(UserDTO::class.java).block()
        }.onFailure {
            throw ResourceNotFoundException("User not found")
        }.getOrThrow()
    }
}
