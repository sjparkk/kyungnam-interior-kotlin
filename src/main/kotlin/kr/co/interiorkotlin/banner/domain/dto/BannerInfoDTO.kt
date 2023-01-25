package kr.co.interiorkotlin.banner.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "인테리어 정보")
data class BannerInfoDTO(

    @field:Schema(title = "이미지 url")
    val imageUrl: List<String>? = null,

)
