package kr.co.interiorkotlin.interior.repository

import kr.co.interiorkotlin.interior.domain.Interior
import kr.co.interiorkotlin.interior.repository.custom.InteriorCustomRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface InteriorRepository: JpaRepository<Interior, Long>, InteriorCustomRepository {

    fun findInteriorsByInteriorCodeIn(codes: List<String>): List<Interior>

    @EntityGraph(attributePaths = ["interiorDetail"])
    fun findByInteriorCode(interiorCode: String): Interior?
}