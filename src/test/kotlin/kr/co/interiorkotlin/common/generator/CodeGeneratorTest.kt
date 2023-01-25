package kr.co.interiorkotlin.common.generator

import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.time.LocalDateTime

internal class CodeGeneratorTest {

    @Test
    fun `LocalDateTime 을 이용한 유니크 값 생성 테스트`() {

        var codeList: MutableList<String> = mutableListOf()

        var i = 1

        while(i <= 5000){
            val timeStr = Timestamp.valueOf(LocalDateTime.now()).time.toString() + Timestamp.valueOf(LocalDateTime.now()).nanos.toString()
            codeList.add(timeStr.substring(timeStr.length-12, timeStr.length-3))
            ++i
        }

        Assertions.assertEquals(codeList.distinct().size, codeList.size, "중복된 값이 없어야 하므로 값이 같아야함")
    }

    @Test
    fun `CodeGenerator 로 코드 생성 시 중복된 코드가 존재 하지 않아야 함`() {

        val codeList: MutableList<String> = mutableListOf()

        var i = 1

        while(i <= 5000){

            val code = "IN"
            val currentTime = Timestamp.valueOf(LocalDateTime.now()).time.toString() + Timestamp.valueOf(LocalDateTime.now()).nanos.toString()
            val subCurrentTime = currentTime.substring(currentTime.length-12, currentTime.length-3)
            val lastCode = RandomStringUtils.randomAlphanumeric(2).uppercase()
            val result = "$code$subCurrentTime$lastCode"

            codeList.add(result)
            ++i
        }

        Assertions.assertEquals(codeList.distinct().size, codeList.size, "중복된 값이 없어야 하므로 값이 같아야함")
    }

}