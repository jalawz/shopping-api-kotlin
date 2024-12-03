package com.menezes.back.end.shopping.repository

import com.menezes.back.end.shopping.dto.ShopReportDTO
import com.menezes.back.end.shopping.model.Shop
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.math.BigInteger
import java.time.LocalDate
import kotlin.math.min

class ReportRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager
) : ReportRepository {
    override fun getShopByFilters(initialDate: LocalDate, endDate: LocalDate?, minValue: Float?): List<Shop> {
        val sb = StringBuilder()
        sb.append("select s ")
        sb.append("from shop s ")
        sb.append("where s.date >= :initialDate ")

        if (endDate != null) {
            sb.append("and s.date <= :endDate ")
        }

        if (minValue != null) {
            sb.append("and s.total <= :minValue ")
        }

        val query = entityManager.createQuery(sb.toString(), Shop::class.java)
        query.setParameter("initialDate", initialDate.atTime(0, 0))

        if (endDate != null) {
            query.setParameter("endDate", endDate.atTime(23, 59))
        }

        if (minValue != null) {
            query.setParameter("minValue", minValue)
        }

        return query.resultList
    }

    override fun getReportByDate(initialDate: LocalDate, endDate: LocalDate): ShopReportDTO {
        val sb = StringBuilder()
        sb.append("select count(sp.id), sum(sp.total), avg(sp.total) ")
        sb.append("from shopping.shop sp ")
        sb.append("where sp.date >= :initialDate ")
        sb.append("and sp.date <= :endDate ")

        val query = entityManager.createNativeQuery(sb.toString())
        query.setParameter("initialDate", initialDate.atTime(0, 0))
        query.setParameter("endDate", endDate.atTime(23, 59))

        val result = query.singleResult as Array<*>
        return ShopReportDTO(
            count = (result[0] as Long).toInt(),
            total = result[1] as Double,
            mean = result[2] as Double
        )
    }
}