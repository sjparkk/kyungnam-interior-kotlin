package kr.co.interiorkotlin.common.exception.handler

import kr.co.interiorkotlin.common.domain.response.CommonResponse
import kr.co.interiorkotlin.common.exception.DataNotFoundException
import kr.co.interiorkotlin.common.exception.ErrorInValidParamException
import kr.co.interiorkotlin.common.exception.enums.CommonError
import kr.co.interiorkotlin.common.exception.enums.ErrorCode
import kr.co.interiorkotlin.common.exception.utils.ErrorUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

/**
 * 공통 Controller Exception Handler
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CommonErrorHandler {
    val log: org.slf4j.Logger = LoggerFactory.getLogger(this::class.java)

    private val CLASS_NAME = "Common"

    @ExceptionHandler(
        value = [
            // HTTP 요청 에러
            HttpMessageNotReadableException::class,
            // 지원되지 않는 HTTP METHOD 에러 핸들러
            HttpRequestMethodNotSupportedException::class,
            // 일치하는 데이터가 없을 때
            DataNotFoundException::class,

            // 인자값 형식 에러
            IllegalArgumentException::class,

            // 입출력 예외 처리
            IOException::class,

            // 밸리데이션 오류
            ErrorInValidParamException::class,

            ConstraintViolationException::class,
        ]
    )
    @Throws(Exception::class)
    fun commonException(e: Exception, request: HttpServletRequest, webRequest: WebRequest): CommonResponse<Any> {

        // 공통 Exception 객체 셋팅
        val error = CommonError.getCommonException(e)
         log.debug("## error = ${e::class.java.simpleName}")
         log.debug("## error = ${e::class.qualifiedName}")
         log.debug("## error = ${e::class.java.name}")
         log.debug("## error = ${e::class.java.canonicalName}")


        val errorTitle =
            if (error.exception) error.message + ExceptionUtils.getRootCauseMessage(e)
            else error.message.ifEmpty { ExceptionUtils.getRootCauseMessage(e) ?: "Empty Error Message" }

        val errorMap: Map<String, Any> = ErrorUtils.setErrorMap(request, webRequest)

        var commonResponse: CommonResponse<Any>? = null

        /* Exception 별 커스텀 처리 */
        when (error) {
            CommonError.UniqueException -> {
                return CommonResponse(ErrorCode.CONFLICT, e.message)
            }

            CommonError.DataNotFoundException -> {
                return CommonResponse(ErrorCode.DATA_NOT_FOUND, e.message)
            }

        }

        commonResponse = CommonResponse(error.errorCode.code, errorTitle, errorMap)

        return commonResponse
    }


    @ExceptionHandler(
        value = [
            Exception::class
        ]
    )
    @Throws(Exception::class)
    fun allException(e: Exception, request: HttpServletRequest, webRequest: WebRequest): CommonResponse<Any> {
        val ERROR_TITLE: String = e.message ?: "Empty Error Message"
        val errorMap = ErrorUtils.setErrorMap(request, webRequest)
        // Level 별 에러메시지 출력
        ErrorUtils.errorWriter(
            CLASS_NAME,
            ErrorUtils.getResErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value()),
            ERROR_TITLE,
            errorMap,
            e
        )
        return CommonResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message
                ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, errorMap
        )
    }

}