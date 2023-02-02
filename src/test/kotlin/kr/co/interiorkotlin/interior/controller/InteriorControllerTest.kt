package kr.co.interiorkotlin.interior.controller

import kr.co.interiorkotlin.interior.service.InteriorService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class InteriorControllerTest(
    val interiorService: InteriorService
) {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("메인 화면 정보 조회 컨트롤러 테스트")
    fun `initInterior`() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/interior/init")
                .param("device_type", "PC")
        )// andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("message").value(HttpStatus.OK.reasonPhrase))
            .andExpect(jsonPath("content").exists())
            // json path의 depth가 깊어지면 .을 추가하여 탐색할 수 있음 (ex : $.content.banner_info)
            .andExpect(jsonPath("$.content.banner_info").exists())
            .andExpect(jsonPath("$.content.interior_info").exists())
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @DisplayName("인테리어 상세 조회 컨트롤러 테스트")
    fun `getInteriorDetail`() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/interior/detail")
                .param("interior_code", "IN620618361BC")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("message").value(HttpStatus.OK.reasonPhrase))
            .andExpect(jsonPath("content").exists())
            .andDo(MockMvcResultHandlers.print())

    }

}