package kr.co.interiorkotlin.contact.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSaveDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class ContactControllerTest(
    val objectMapper: ObjectMapper
) {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `견적 문의 생성 + Image file`() {

        //given
        val file = MockMultipartFile(
            "file",
            "imagefile.jpeg",
            MediaType.IMAGE_JPEG_VALUE,
            "<<jpeg data>>".toByteArray()
        )

        val content =
            objectMapper.writeValueAsString(
                ReqContactSaveDTO(
                    interiorType = "아파트",
                    name = "도곡동 자이아파트",
                    phone = "010-5231-2315",
                    email = "dogok@gmail.com",
                    postCode = "12341",
                    address = "서울특별시",
                    addressDetail = "강남구 도곡동",
                    roomCnt = 2,
                    bathroomCnt = 2,
                    space = 35,
                    spaceType = "평",
                    budget = "3000",
                    callTime = "10시",
                    password = "1234",
                    content = "문의합니다",
                    accessRoute = "블로그"
                )
            )

        val reqContactSaveDTO = MockMultipartFile(
            "reqContactSaveDTO",
            "jsonData",
            MediaType.APPLICATION_JSON_VALUE,
            content.toByteArray()
        )

        mockMvc
            .perform(
                multipart("/api/v1/contact")
                    .file(file)
                    .file(reqContactSaveDTO)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
    }


}