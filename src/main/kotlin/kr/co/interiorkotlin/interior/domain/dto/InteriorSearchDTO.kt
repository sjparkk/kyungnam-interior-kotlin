package kr.co.interiorkotlin.interior.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "인테리어 리스트 정보", hidden = true)
data class InteriorSearchDTO(
    @field:Schema(title = "인테리어 고유 코드")
    val interiorCode: String? = null,

    @field:Schema(title = "제목")
    val title: String? = null,

    @field:Schema(title = "내용")
    val content: String? = null,

    @field:Schema(title = "이미지 url")
    val imageUrl: String? = null,

    @field:Schema(title = "썸네일 url")
    val thumbnailUrl: String? = null,

    @field:Schema(title = "인테리어 타입", description = "아파트/빌라/주택/상가/사무실")
    val interiorType: String? = null,

    @field:Schema(title = "평수")
    val space: Int? = null,

    @field:Schema(title = "주소")
    val address: String? = null,

    @field:Schema(title = "태그 정보")
    var tags: List<String>? = null,

    @field:Schema(title = "등록일시")
    var createdAt: LocalDateTime? = null,

    @field:Schema(title = "수정일시")
    var updatedAt: LocalDateTime? = null,
)
