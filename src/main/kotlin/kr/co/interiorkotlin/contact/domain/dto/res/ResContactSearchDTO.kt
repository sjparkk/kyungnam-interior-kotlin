package kr.co.interiorkotlin.contact.domain.dto.res

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.page.domain.ResPageDTO
import kr.co.interiorkotlin.contact.domain.dto.ContactDTO

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "견적 문의 리스트 응답", hidden = true)
data class ResContactSearchDTO(

    @field:Schema(title = "견적 문의 리스트 정보")
    val data: ResPageDTO<ContactDTO>
)