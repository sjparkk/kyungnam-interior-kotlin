package kr.co.interiorkotlin.contact.domain.dto.res

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "견적 문의 상세 조회 응답", hidden = true)
data class ResContactDetailDTO(

    @field:Schema(title = "interior_type", description = "인테리어 타입 - 아파트/빌라/상가/주택/사무실")
    val interiorType: String? = null,

    @field:Schema(title = "title", description = "고객명")
    val name: String? = null,

    @field:Schema(title = "phone", description = "번호")
    val phone: String? = null,

    @field:Schema(title = "email", description = "이메일")
    val email: String? = null,

    @field:Schema(title = "post_code", description = "우편번호")
    val postCode: String? = null,

    @field:Schema(title = "address", description = "주소")
    val address: String? = null,

    @field:Schema(title = "address_detail", description = "상세주소")
    val addressDetail: String? = null,

    @field:Schema(title = "room_cnt", description = "방개수")
    val roomCnt: Int? = null,

    @field:Schema(title = "bathroom_cnt", description = "화장실개수")
    val bathroomCnt: Int? = null,

    @field:Schema(title = "space", description = "평수 or 면적")
    val space: Int? = null,

    @field:Schema(title = "space_type", description = "타입에 따라 평수 or 면적 -> 면적일시 평수로 변환해주는 로직")
    val spaceType: String? = null,

    @field:Schema(title = "budget", description = "예산")
    val budget: String? = null,

    @field:Schema(title = "call_time", description = "통화 가능 시간")
    val callTime: String? = null,

    @field:Schema(title = "content", description = "문의내용")
    val content: String? = null,

    @field:Schema(title = "access_route", description = "접근 경로")
    val accessRoute: String? = null,


)