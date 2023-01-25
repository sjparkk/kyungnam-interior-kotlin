package kr.co.interiorkotlin.common.exception.domain.dto

/**
 * Error 응답 클래스
 */
data class ResErrorDTO (
    val code: Int? = null,
    val message: String? = null
)