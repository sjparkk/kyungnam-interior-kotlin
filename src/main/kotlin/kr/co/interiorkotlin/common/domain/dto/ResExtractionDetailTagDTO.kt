package kr.co.interiorkotlin.common.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ResExtractionDetailTagDTO(
    val keyword: String = "",
    val weight: Float = 0f
)