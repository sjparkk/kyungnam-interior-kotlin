package kr.co.interiorkotlin.common.page.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "페이지 관련 정보", hidden = true)
data class PageInfoDTO(

    @field:Schema(title = "상품 정보")
    val totalElements: Long = 0,

    @field:Schema(title = "상품 정보")
    val page: Int = 0,

    @field:Schema(title = "상품 정보")
    val size: Int = 0,
)