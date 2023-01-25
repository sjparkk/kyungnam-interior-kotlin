package kr.co.interiorkotlin.common.domain.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.exception.enums.ErrorCode
import org.springframework.http.HttpStatus

@Schema(title = "공통 응답")
class CommonResponse<T>(
    @field:Schema(title = "응답 코드", example = "200")
    val code: Int,
    @field:Schema(title = "응답 메세지", example = "SUCCESS")
    val message: String,
    @field:Schema(title = "응답 데이터")
    var content: T?
) {

    constructor() : this(HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, null)

    constructor(content: T) : this(HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, content)

    constructor(httpStatus: HttpStatus, message: String? = null) : this(httpStatus.value(), message ?: httpStatus.reasonPhrase, null)

    constructor(httpStatus: HttpStatus, content: T) : this(httpStatus.value(), httpStatus.reasonPhrase, content)

    constructor(errorCode: ErrorCode, message: String? = null) : this(errorCode.code, message ?: errorCode.message, null)

    constructor(errorCode: ErrorCode, content: T) : this(errorCode.code, errorCode.message, content)

    override fun toString(): String {
        return "CommonResponse(code=$code, message='$message', content=$content)"
    }


}