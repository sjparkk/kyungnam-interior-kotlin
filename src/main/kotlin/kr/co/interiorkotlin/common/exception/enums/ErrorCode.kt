package kr.co.interiorkotlin.common.exception.enums

import org.slf4j.event.Level
import kr.co.interiorkotlin.common.exception.enums.ErrorCode.*

/**
 * Exception 타입에 따른 메세지 처리 상수
 */
enum class CommonError(
    /* 응답코드객체 */
    val errorCode: ErrorCode,
    /* 응답메세지 */
    val message: String,
    /* e.getMessage true:추가, false:미추가 */
    val exception: Boolean,
    /* Log Level */
    var level: Level
    ) {

    /** 기본 서버 에러 **/
    HttpServerErrorException(ERROR, "", true, Level.ERROR),

    /** 지원되지 않는 HTTP METHOD 에러 핸들러 **/
    HttpRequestMethodNotSupportedException(UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 HTTP METHOD :: ", false, Level.ERROR),

    /** 일치하는 데이터가 없을 때 **/
    DataNotFoundException(DATA_NOT_FOUND, "요청한 데이터를 찾을 수 없습니다 :: ", true, Level.ERROR),

    /** 허용되지 않은 권한 예외 처리 **/
    UnAuthorizedException(UNAUTHORIZED, "허용되지 않는 권한입니다.", true, Level.ERROR),

    UniqueException(CONFLICT, "이미 사용중인 데이터 입니다.", true, Level.WARN),

    ErrorInValidParamException(ERROR_INVALID_PARAM, "파라메터 유효성 검증 실패 ::", true, Level.WARN),

    BadRequestException(ERROR_REQUEST, "잘못된 요청", true, Level.WARN),
    ;

    companion object {

        var nameToMap: MutableMap<String, CommonError> = mutableMapOf()

        /**
         * 코드 값으로 ResCode 맵으로 맵핑
         * @param code
         * @return
         */
        fun getCommonException(e: Exception): CommonError {
            if(nameToMap.isEmpty())
                initMapping()
            return nameToMap[e::class.java.simpleName]
                ?: HttpServerErrorException
        }

        /**
         * 맵 초기화
         */
        private fun initMapping() {
            nameToMap = mutableMapOf()
            values().forEach {
                nameToMap[it.name] = it
            }
        }

    }

}