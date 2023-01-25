package kr.co.interiorkotlin.interior.domain.dto.req

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import org.springdoc.api.annotations.ParameterObject
import javax.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@ParameterObject
@Schema(title = "인테리어 메인 화면 요청")
data class ReqInteriorInitDTO(

    @field:NotBlank
    @field:Parameter(name = "device_type", description = "요청 기기 타입 - PC, MOBILE")
    val deviceType: DeviceTypeEnum,

    )