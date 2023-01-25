package kr.co.interiorkotlin.banner.repository

import kr.co.interiorkotlin.banner.domain.Banner
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository: JpaRepository<Banner, Long> {

    fun findTop3ByPositionTypeOrderByOrderAsc(positionType: String): List<Banner>

}