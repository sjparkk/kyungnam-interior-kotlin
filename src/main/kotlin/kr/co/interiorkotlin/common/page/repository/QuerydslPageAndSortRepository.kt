package kr.co.interiorkotlin.common.page.repository

import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import kr.co.interiorkotlin.common.page.domain.QueryDslPage
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.Querydsl
import javax.persistence.EntityManager
import kotlin.reflect.KClass

/**
 * QuerydslRepositorySupport 참고하여 생성
 */
open class QuerydslPageAndSortRepository(
    private val entityManager: EntityManager,
    private val clazz: KClass<*>
) {

    fun getQuerydsl(): Querydsl {
        val builder = PathBuilderFactory().create(clazz.java)
        return Querydsl(entityManager, builder)
    }

    fun <T> getPageImpl(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        return if (query.metadata.groupBy.size > 0) {
            getPageImplIfGroupBy(pageable, query)
        } else {
            getPageImplIfNotGroupBy(pageable, query)
        }
    }

    fun <T> getQueryResult(pageable: Pageable, query: JPQLQuery<T>): QueryDslPage<T> {
        val totalCount = query.fetchCount()

        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return QueryDslPage(results, totalCount)
    }

    private fun <T> getPageImplIfGroupBy(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val queryResult = query.fetch()
        val totalCount = queryResult.size

        val offset = pageable.offset

        // totalCount 보다 큰 값이 들어온 경우
        if (offset > totalCount) {
            return PageImpl(listOf(), pageable, totalCount.toLong())
        }

        // limit 설정
        var limit = pageable.pageSize * (pageable.pageNumber + 1)
        limit = if (limit > totalCount) {
            totalCount
        } else {
            limit
        }

        val results = queryResult.subList(offset.toInt(), limit)
        return PageImpl(results, pageable, totalCount.toLong())
    }

    private fun <T> getPageImplIfNotGroupBy(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val totalCount = query.fetchCount()

        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return PageImpl(results, pageable, totalCount)
    }
}