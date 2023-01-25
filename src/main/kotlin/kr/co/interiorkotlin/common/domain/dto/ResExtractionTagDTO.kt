package kr.co.interiorkotlin.common.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ResExtractionTagDTO(
    val result: Array<ResExtractionDetailTagDTO> = arrayOf(),
    val elapsedTime: String = "",
    val version: String = "",
    val lang: String = "",
    val maxCandidateNum: Int = 0,
    val code: Int = 0,
    val msg: String = ""
)