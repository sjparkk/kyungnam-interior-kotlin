package kr.co.interiorkotlin.contact.service

import kr.co.interiorkotlin.contact.repository.ContactRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ContactServiceTest(
    val contactRepository: ContactRepository
) {

    @Test
    fun `금일 0시 0분 0초 부터 조회 시점 사이 값 조회`() {

        //given
        val nowDate = LocalDate.now()
        val start = nowDate.atTime(0, 0, 0)
        val end = LocalDateTime.now()

        //when
        val result = contactRepository.findContactsByCreatedAtBetween(start, end)
        println(result.toString())

        //then
        assertEquals(2, result.size)

    }
}