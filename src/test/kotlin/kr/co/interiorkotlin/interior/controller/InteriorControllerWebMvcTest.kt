package kr.co.interiorkotlin.interior.controller

import kr.co.interiorkotlin.banner.domain.dto.BannerInfoDTO
import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorInitDTO
import kr.co.interiorkotlin.interior.service.InteriorService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

/**
 * Interior controller web mvc test
 * @WebMvcTest 는 Controller를 (단위)테스트하기 위한 어노테이션이다.
 *
 * Controller만 정상적으로 동작하는지 테스트하는 것이기 때문에 Web 과 관련된 의존성만을 가지고 온다.
 *
 * WebMvcTest에서 가져오는 의존성들 즉, 다음 어노테이션들만 ComponentScan해서 가져온다.
 * @Controller, @ControllerAdvice, @JsonComponent, Converter, GenericConverter, Filter, HandlerInterceptor
 *
 */
@WebMvcTest(InteriorController::class)
class InteriorControllerWebMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    //@Service 는 의존성을 가지고 오지 않기 때문에 @MockBean을 선언하지 않으면 WebMvcTest 에러 발생.
    @MockBean
    lateinit var interiorService: InteriorService

    //컨트롤러의 정상 작동 여부만은 확인하는 것이고, 컨트롤러의 실제 응답값을 확인하기 위해서는 통합테스트를 진행 (@SpringBootTest 를 사용하여 애플리케이션 내의 모든 스프링 빈 가져옴.)
    @Test
    fun `init controller WebMvcTest`() {

        //given
        val initDTO = ResInteriorInitDTO(
                bannerInfo = BannerInfoDTO(
                    imageUrl = listOf("testUrl.com")
                ),
                interiorInfo = listOf()
            )

        given(interiorService.initInterior(DeviceTypeEnum.PC))
            .willReturn(initDTO)


        //when, then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/interior/init")
                .param("device_type", "PC")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.content.banner_info.image_url[0]").value("testUrl.com"))
            .andDo(MockMvcResultHandlers.print())

    }
}