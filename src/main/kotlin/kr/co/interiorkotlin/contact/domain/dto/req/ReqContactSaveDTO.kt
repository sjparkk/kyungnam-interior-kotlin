package kr.co.interiorkotlin.contact.domain.dto.req

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "견적 문의 요청")
data class ReqContactSaveDTO(

    @field:NotBlank
    @field:Schema(title = "interior_type", description = "인테리어 타입 - 아파트/빌라/상가/주택/사무실")
    val interiorType: String,

    @field:NotBlank
    @field:Schema(title = "title", description = "고객명")
    val name: String,

    @field:NotBlank
    @field:Schema(title = "phone", description = "번호")
    val phone: String,

    @field:Schema(title = "email", description = "이메일")
    val email: String? = null,

    @field:NotBlank
    @field:Schema(title = "post_code", description = "우편번호")
    val postCode: String,

    @field:NotBlank
    @field:Schema(title = "address", description = "주소")
    val address: String,

    @field:NotBlank
    @field:Schema(title = "address_detail", description = "상세주소")
    val addressDetail: String,

    @field:Min(1)
    @field:Schema(title = "room_cnt", description = "방개수")
    val roomCnt: Int,

    @field:Min(1)
    @field:Schema(title = "bathroom_cnt", description = "화장실개수")
    val bathroomCnt: Int,

    @field:Min(1)
    @field:Schema(title = "space", description = "평수 or 면적")
    val space: Int,

    @field:Schema(title = "space_type", description = "타입에 따라 평수 or 면적 -> 면적일시 평수로 변환해주는 로직")
    val spaceType: String? = null,

    @field:NotBlank
    @field:Schema(title = "budget", description = "예산")
    val budget: String,

    @field:NotBlank
    @field:Schema(title = "call_time", description = "통화 가능 시간")
    val callTime: String,

    @field:NotBlank
    @field:Schema(title = "password", description = "게시글 비밀번호")
    val password: String,

    @field:NotBlank
    @field:Schema(title = "content", description = "문의내용")
    val content: String,

    @field:Schema(title = "access_route", description = "접근 경로")
    val accessRoute: String? = null,

    )