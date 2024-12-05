package com.menezes.back.end.shopping.repository

import com.menezes.back.end.shopping.model.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ShopRepository : JpaRepository<Shop, Long>, ReportRepository {
    fun findAllByUserIdentifier(userIdentifier: String): List<Shop>

    fun findAllByTotalGreaterThan(total: Float): List<Shop>

    fun findAllByDateGreaterThan(date: LocalDateTime): List<Shop>
}
