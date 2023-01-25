package kr.co.interiorkotlin.contact.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "견적 문의 정보", hidden = true)
data class ContactDTO(
    @field:Schema(title = "인테리어 타입", description = "아파트/빌라/상가/주택/사무실")
    var interiorType: String? = null,

    @field:Schema(title = "고객명")
    var name: String? = null,

    @field:Schema(title = "번호")
    var phone: String? = null,

    @field:Schema(title = "이메일")
    var email: String? = null,

    @field:Schema(title = "우편번호")
    var postCode: String? = null,

    @field:Schema(title = "주소")
    var address: String? = null,

    @field:Schema(title = "상세주소")
    var addressDetail: String? = null,

    @field:Schema(title = "방개수")
    var roomCnt: Int? = null,

    @field:Schema(title = "화장실개수")
    var bathroomCnt: Int? = null,

    @field:Schema(title = "평수 or 면적")
    var space: Int? = null,

    @field:Schema(title = "타입에 따라 평수 or 면적 -> 면적일시 평수로 변환해주는 로직")
    var spaceType: String? = null,

    @field:Schema(title = "예산")
    var budget: String? = null,

    @field:Schema(title = "통화 가능 시간")
    var callTime: String? = null,

    @field:Schema(title = "문의내용")
    var content: String? = null,

    @field:Schema(title = "접근 경로")
    var accessRoute: String? = null,

) {
}