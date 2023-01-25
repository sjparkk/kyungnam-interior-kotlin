package kr.co.interiorkotlin.common.exception.enums

import org.springframework.http.HttpStatus

/**
 * ErrorCode 응답 오류 코드 정의
 */
enum class ErrorCode(val code: Int, val message: String) {
    //2XX
    DATA_NOT_FOUND(HttpStatus.NO_CONTENT.value(),"일치하는 데이터가 없습니다"),
    CONFLICT(HttpStatus.CONFLICT.value(),"이미 사용 중인 데이터 입니다."),
    //4XX
    ERROR_REQUEST(HttpStatus.BAD_REQUEST.value(),"잘못된 요청 :: 요청 정보를 확인하세요"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "권한없음"),
    ERROR_INVALID_PARAM(405,"파라메터 유효성 검증 실패"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"지원하지 않는 HTTP 메서드입니다"),
    //5XX
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버에러가 발생했습니다. 서버 관리자에게 문의하세요"),
    ;

    companion object {

        var codeToMap: MutableMap<Int, ErrorCode> = mutableMapOf()

        /**
         * 코드 값으로 ResCode 맵으로 맵핑
         * @param code
         * @return
         */
        fun getErrorCode(code: Int): ErrorCode? {
            if(codeToMap.isEmpty())
                initMapping()
            return codeToMap[code]
        }

        /**
         * 맵 초기화
         */
        private fun initMapping() {
            codeToMap = mutableMapOf()
            values().forEach {
                codeToMap[it.code] = it
            }
        }

    }

}