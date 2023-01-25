package kr.co.interiorkotlin.contact.domain.dto.req

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.api.annotations.ParameterObject

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@ParameterObject
@Schema(title = "견적 문의 조회 요청")
class ReqContactSearchDTO(

    @field:Parameter(name = "type", description = "검색 타입 - 이름, 주소")
    val type: String? = null,

    @field:Parameter(name = "keyword", description = "검색어")
    val keyword: String? = null,

    ) {

    fun valid() {
        if (type != null && keyword == null) {
            throw Exception("nono")
        } else if (type == null && keyword != null) {
            throw Exception("nono")
        }
    }
}