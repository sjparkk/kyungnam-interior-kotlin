package kr.co.interiorkotlin.interior.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import kr.co.interiorkotlin.common.domain.response.CommonResponse
import kr.co.interiorkotlin.common.page.domain.PageRequestDTO
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorDetailDTO
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorInitDTO
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorSearchDTO
import kr.co.interiorkotlin.interior.service.InteriorService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotEmpty

@Tag(name = "인테리어", description = "InteriorController")
@RestController
@RequestMapping("/api/v1/interior")
class InteriorController(
    val interiorService: InteriorService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Operation(summary = "메인 화면 정보 조회", description = """
        - page_content 의 저장된 정보를 토대로 메인 화면 필요 정보 조회
    """)
    @GetMapping("/init")
    fun initInterior(
        @Parameter(description = "요청 기기 타입 - PC, MOBILE", example = "PC", required = true)
        @NotEmpty @RequestParam("device_type") deviceType: DeviceTypeEnum
    ): CommonResponse<ResInteriorInitDTO> {
        val response = interiorService.initInterior(deviceType)
        log.debug(":: 메인 화면 정보 조회 응답 - $response ")
        return CommonResponse(response)
    }

    @Operation(summary = "인테리어 작업물 리스트 조회")
    @GetMapping("/list")
    fun getInteriorList(
        pageRequest: PageRequestDTO,
        @Parameter(description = "평수", example = "20", required = false)
        @NotEmpty @RequestParam("space") space: Int? = null,
        @Parameter(description = "인테리어 타입 - 아파트/빌라/주택/상가/사무실", example = "아파트", required = false)
        @NotEmpty @RequestParam("interior_type") interiorType: String? = null
    ): CommonResponse<ResInteriorSearchDTO> {
        val response = interiorService.getInteriorList(pageRequest.getPageable(), space, interiorType)
        log.debug(":: 인테리어 작업물 리스트 조회 응답 - $response ")
        return CommonResponse(ResInteriorSearchDTO(response))
    }

    @Operation(summary = "인테리어 상세 조회")
    @GetMapping("/detail")
    fun getInteriorDetail(
        @Parameter(description = "인테리어 CODE", example = "IN620618361BC", required = true)
        @NotEmpty @RequestParam("interior_code") interiorCode: String
    ): CommonResponse<ResInteriorDetailDTO> {
        val response = interiorService.getInterior(interiorCode)
        log.debug(":: 인테리어 상세 조회 응답 - $response ")
        return CommonResponse(response)
    }

}