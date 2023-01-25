package kr.co.interiorkotlin.common.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ReqExtractionTagDTO(
    val sentences: Array<String>,
    val lang: String,
    val maxCandidateNum: Int
)