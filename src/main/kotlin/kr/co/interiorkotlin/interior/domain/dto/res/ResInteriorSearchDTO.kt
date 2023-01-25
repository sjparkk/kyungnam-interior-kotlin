package kr.co.interiorkotlin.interior.domain.dto.res

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.page.domain.ResPageDTO
import kr.co.interiorkotlin.interior.domain.dto.InteriorSearchDTO

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "인테리어 리스트 응답", hidden = true)
data class ResInteriorSearchDTO(

    @field:Schema(title = "인테리어 리스트 정보")
    val data: ResPageDTO<InteriorSearchDTO>
)