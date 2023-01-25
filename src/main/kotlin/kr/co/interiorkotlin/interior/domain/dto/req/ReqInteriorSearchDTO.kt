package kr.co.interiorkotlin.interior.domain.dto.req

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.api.annotations.ParameterObject

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@ParameterObject
@Schema(title = "인테리어 리스트 조회 요청")
data class ReqInteriorSearchDTO(

    @field:Parameter(name = "space", description = "평수")
    val space: Int? = null,

    @field:Parameter(name = "interior_type", description = "인테리어 타입 - 아파트/빌라/주택/상가/사무실")
    val interiorType: String? = null,

    )