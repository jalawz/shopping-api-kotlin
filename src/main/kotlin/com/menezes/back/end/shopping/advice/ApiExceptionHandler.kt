package com.menezes.back.end.shopping.advice

import com.menezes.back.end.shopping.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            message = ex.message ?: "Resource Not Found",
            status = HttpStatus.NOT_FOUND.value(),
            timestamp = Instant.now()
        )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}


data class ErrorMessage(
    val message: String,
    val status: Int,
    val timestamp: Instant
)