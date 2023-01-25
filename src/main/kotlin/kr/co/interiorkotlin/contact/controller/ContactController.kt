package kr.co.interiorkotlin.contact.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.interiorkotlin.common.domain.response.CommonResponse
import kr.co.interiorkotlin.common.page.domain.PageRequestDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSaveDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSearchDTO
import kr.co.interiorkotlin.contact.domain.dto.res.ResContactDetailDTO
import kr.co.interiorkotlin.contact.domain.dto.res.ResContactSearchDTO
import kr.co.interiorkotlin.contact.service.ContactService
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Tag(name = "견적 문의", description = "ContactController")
@Validated
@RestController
@RequestMapping("/api/v1/contact")
class ContactController(
    val contactService: ContactService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Operation(summary = "견적 문의 생성", description = """
        - 견적 문의 생성 후 관리자 슬랙 알람
    """)
    @PostMapping("/save")
    fun saveContact(
        @Valid @RequestBody reqContactSaveDTO: ReqContactSaveDTO
    ): CommonResponse<Unit> {
        val response = contactService.saveContact(reqContactSaveDTO)
        log.debug("::견적 문의 생성 응답 - $response")
        return CommonResponse(response)
    }

    @Operation(summary = "견적 문의 리스트 조회")
    @GetMapping("/list")
    fun getContactList(
        pageRequest: PageRequestDTO,
        @Valid reqContactSearchDTO: ReqContactSearchDTO
    ): CommonResponse<ResContactSearchDTO> {

        reqContactSearchDTO.valid()

        val response = contactService.getContactList(pageRequest.getPageable(), reqContactSearchDTO)
        return CommonResponse(ResContactSearchDTO(response))
    }

    @Operation(summary = "견적 문의 상세 조회", description = """
        - 나의 견적 문의만 상세 확인 가능
        - 견적 문의 상세 확인 시 최초 견적 문의 시 설정 했던 password 가 일치 해야 함
    """)
    @GetMapping("/detail/{id}")
    fun getContactDetail(
        @Parameter(description = "견적 문의 ID", example = "1", required = true)
        @NotNull @PathVariable("id") contactId: Long,
        @Parameter(description = "초기 설정 했던 견적 문의 비밀번호", example = "1010", required = true)
        @NotEmpty @RequestParam("password") password: String
    ): CommonResponse<ResContactDetailDTO> {
        val response = contactService.getContact(contactId, password)
        return CommonResponse(response)
    }


}