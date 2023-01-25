package kr.co.interiorkotlin.interior.domain.dto.res

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import kr.co.interiorkotlin.banner.domain.dto.BannerInfoDTO
import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.interior.domain.dto.InteriorInfoDTO

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "인테리어 메인 화면 정보")
data class ResInteriorInitDTO(

    @field:Schema(title = "배너 정보")
    val bannerInfo: BannerInfoDTO? = null,

    @field:Schema(title = "상품 정보")
    val interiorInfo: List<InteriorInfoDTO>? = null,

    )