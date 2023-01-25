package kr.co.interiorkotlin.common.generator

import kr.co.interiorkotlin.common.exception.ErrorInValidParamException
import org.apache.commons.lang3.RandomStringUtils
import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * 코드 생성기
 */
class CodeGenerator {

    companion object {

        /**
         * 랜덤코드 생성
         *
         * 요구사항 : 총 13자리
         *
         * 조합별 조건 - IN + 숫자9 + BC
         * 첫 두자리 - IN : 생성 케이스별 고유 코드 (케이스 아래 참조)
         * 중간 아홉자리 - 숫자9 : LocalDateTime.now() -> Timestamp time + nano 단위 변환 후 끝 4번째부터 9자리까지 (시분초까지는 중복되는 케이스 존재하여 나노 단위부터 뒤에서 자름)
         * 마지막 두자리 - BC : 숫자 + 대문자 완전랜덤 -> MySQL varchar 타입 대소문자 구분 x 으로 인하여 대문자로 통일
         *
         * 예상코드1 : IN 620618361 BC
         *
         * IN - 인테리어 코드 (Interior)
         */
        fun RANDOM(code: String = "", datetime: LocalDateTime = LocalDateTime.now()): String {
            if (code.length > 3)
                throw ErrorInValidParamException("code의 자리수는 2를 초과할 수 없습니다")

            // 나노세컨트 단위까지 합쳐서 자름 - 9자리
            val currentTime =
                Timestamp.valueOf(LocalDateTime.now()).time.toString() + Timestamp.valueOf(LocalDateTime.now()).nanos.toString()
            val subCurrentTime = currentTime.substring(currentTime.length - 12, currentTime.length - 3)

            // MySql varchar 타입 대소문 구분이 없으므로 대문자로 고정
            val lastCode = RandomStringUtils.randomAlphanumeric(2).uppercase()
            return "$code$subCurrentTime$lastCode"
        }
    }
}