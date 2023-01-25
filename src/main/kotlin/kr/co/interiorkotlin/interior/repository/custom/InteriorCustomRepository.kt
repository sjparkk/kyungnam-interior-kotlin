package kr.co.interiorkotlin.interior.repository.custom

import kr.co.interiorkotlin.interior.domain.dto.InteriorSearchDTO
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface InteriorCustomRepository {

    fun findInteriorBySpaceAndType(pageable: Pageable,  space: Int?, interiorType: String?): PageImpl<InteriorSearchDTO>
}