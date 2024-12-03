package com.menezes.back.end.shopping.repository

import com.menezes.back.end.shopping.dto.ShopReportDTO
import com.menezes.back.end.shopping.model.Shop
import java.time.LocalDate

interface ReportRepository {

    fun getShopByFilters(
        initialDate: LocalDate,
        endDate: LocalDate? = null,
        minValue: Float? = null
    ): List<Shop>

    fun getReportByDate(
        initialDate: LocalDate,
        endDate: LocalDate
    ): ShopReportDTO
}