package kr.co.interiorkotlin.common.page.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "공통 페이지 응답 정보", hidden = true)
data class ResPageDTO<T> (

    @field:Schema(title = "응답 데이터 정보")
    val list: List<T>? = null,

    @field:Schema(title = "페이지 관련 정보")
    val pageInfo: PageInfoDTO,
) {
    constructor(data: PageImpl<T>) : this(
        data.content, PageInfoDTO(
            totalElements = data.totalElements,
            page = data.number,
            size = data.size
        )
    )

    constructor(data: Page<T>) : this(
        data.content, PageInfoDTO(
            totalElements = data.totalElements,
            page = data.number,
            size = data.size
        )
    )

    constructor(pageInfo: PageInfoDTO, data: List<T>) : this(data, pageInfo)

}