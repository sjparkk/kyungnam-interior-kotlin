package kr.co.interiorkotlin.common.exception.utils

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.interiorkotlin.common.exception.domain.dto.ResErrorDTO
import kr.co.interiorkotlin.common.exception.enums.ErrorCode
import kr.co.interiorkotlin.common.utils.JsonUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest

class ErrorUtils {

    companion object {

        val log: Logger = LoggerFactory.getLogger(this::class.java)

        /**
         * ERROR CODE 로 ResErrorDTO 응답 객체 생성
         * @param code Int
         * @return ResErrorDTO
         */
        fun getResErrorDTO(code: Int): ResErrorDTO {
            val errorCode = ErrorCode.getErrorCode(code)
            if(errorCode != null)
                return ResErrorDTO(errorCode.code, errorCode.message)
            val httpStatus = HttpStatus.valueOf(code)
            return ResErrorDTO(httpStatus.value(), httpStatus.reasonPhrase)
        }

        /**
         * Exception Handler ErrorMap 기본 셋팅
         * @param request
         * @throws IOException
         */
        @Throws(Exception::class)
        fun setErrorMap(request: HttpServletRequest, webRequest: WebRequest): Map<String, Any> {
            return setErrorMap(request, webRequest, linkedMapOf())
        }

        /**
         * Exception Handler ErrorMap 기본 셋팅
         * @param request
         * @throws IOException
         */
        @Throws(Exception::class)
        fun setErrorMap(
            request: HttpServletRequest,
            webRequest: WebRequest,
            reqErrorMap: LinkedHashMap<String, Any>,
        ): Map<String, Any> {
            val objectMapper = ObjectMapper()
            val errorMap = getErrorMap(reqErrorMap, request) //에러 맵 생성

            var body = webRequest.getAttribute("body", RequestAttributes.SCOPE_REQUEST)
            if (body is String) body = objectMapper.readValue(body, MutableMap::class.java)
            if(body != null)
                errorMap["Request Body"] = body
            return errorMap
        }

        /**
         * Error Map 생성
         * 에러 메세지 맵을 생성하여 메신저에 전송한다
         * @param errorMap
         * @param request
         * @return
         */
        private fun getErrorMap(reqErrorMap: LinkedHashMap<String, Any>, request: HttpServletRequest): LinkedHashMap<String, Any>{
            val errorMap: LinkedHashMap<String, Any> = reqErrorMap
            if (request.getHeader("Content-Type") != null) errorMap["Content-Type"] = request.getHeader("Content-Type")

            errorMap["Request URI"] = request.requestURI
            errorMap["HttpMethod"] = request.method
            errorMap["Servlet Path"] = request.servletPath
            errorMap["Client IP"] = request.localAddr
            errorMap["QueryString"] = request.queryString ?: ""
            errorMap["Parameters"] = request.parameterMap

            return errorMap
        }


        /**
         * 에러메세지 출력
         * @param CLASS_NAME
         * @param ERROR_TITLE
         * @param errorMap
         * @param e
         */
        fun errorWriter(CLASS_NAME: String, resErrorDTO: ResErrorDTO, ERROR_TITLE: String, errorMap: Map<String, Any>, e: Exception) {
            log.error("[$CLASS_NAME] ERROR 메세지 :: $ERROR_TITLE\n\n응답 코드 :: ${resErrorDTO.code}\n응답 메세지 :: ${resErrorDTO.message}\n요청 정보 ::\n" +
                    "${JsonUtils.toMapperPrettyJson(errorMap)}\n\nException ::\n${e.message}", e)
        }

    }
}