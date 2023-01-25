package kr.co.interiorkotlin.interior.repository.custom

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.interiorkotlin.interior.domain.QInterior.interior
import kr.co.interiorkotlin.common.page.repository.QuerydslPageAndSortRepository
import kr.co.interiorkotlin.interior.domain.Interior
import kr.co.interiorkotlin.interior.domain.dto.InteriorSearchDTO
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager

class InteriorCustomRepositoryImpl(
    entityManager: EntityManager,
    private val queryFactory: JPAQueryFactory
): InteriorCustomRepository, QuerydslPageAndSortRepository(entityManager, Interior::class) {
    override fun findInteriorBySpaceAndType(pageable: Pageable, space: Int?, interiorType: String?): PageImpl<InteriorSearchDTO> {
        val query = queryFactory
            .select(
                Projections.fields(
                    InteriorSearchDTO::class.java,
                    interior.interiorCode,
                    interior.title,
                    interior.content,
                    interior.imageUrl,
                    interior.thumbnailUrl,
                    interior.interiorType,
                    interior.space,
                    interior.address,
                    interior.createdAt,
                    interior.updatedAt
                )
            )
            .from(interior)
            .where(
                searchConditions(space, interiorType)
            )

        return super.getPageImpl(pageable, query)
    }

    private fun searchConditions(space: Int?, type: String?): Predicate {
        val builder = BooleanBuilder()

        //평수
        space?.let { builder.and(interior.space.eq(it)) }

        //인테리어 타입
        type?.let {  builder.and(interior.interiorType.eq(it)) }

        return builder
    }
}