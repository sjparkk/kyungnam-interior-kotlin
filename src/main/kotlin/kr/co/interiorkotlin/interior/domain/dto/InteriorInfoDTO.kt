package kr.co.interiorkotlin.interior.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "인테리어 정보")
data class InteriorInfoDTO(

    @field:Schema(title = "인테리어 고유 코드")
    val interiorCode: String? = null,

    @field:Schema(title = "제목")
    val title: String? = null,

    @field:Schema(title = "썸네일 url")
    val thumbnailUrl: String? = null,

    @field:Schema(title = "태그 정보", description = "TagExtractionUtils 를 통해 추출")
    val tags: List<String>? = null,

    @field:Schema(title = "인테리어 ID")
    val id: Long? = null,
) {
}