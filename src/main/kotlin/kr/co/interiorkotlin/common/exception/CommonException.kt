package kr.co.interiorkotlin.common.exception

import kr.co.interiorkotlin.common.exception.enums.ErrorCode
import org.springframework.http.HttpStatus

/**
 * Exception 커스텀 예외 클래스
 */
open class CommonException: RuntimeException {
    private var errorCode: ErrorCode = ErrorCode.ERROR
    private var errorMessage: String = ""
    private var httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
    private var errorData: String = ""

    constructor(errorCode: ErrorCode) : this() {
        this.errorCode = errorCode
        this.errorMessage = errorCode.message
    }

    constructor(errorCode: ErrorCode, httpStatus: HttpStatus) : this() {
        this.errorCode = errorCode
        this.errorMessage = errorCode.message
        this.httpStatus = httpStatus
    }

    constructor(errorCode: ErrorCode, errorMessage: String) : this() {
        this.errorCode = errorCode
        this.errorMessage = errorCode.message
    }

    constructor(errorCode: ErrorCode, errorMessage: String, errorData: String) : this() {
        this.errorCode = errorCode
        this.errorMessage = errorCode.message
        this.errorData = errorData
    }

    constructor(httpStatus: HttpStatus, errorMessage: String) : this() {
        this.errorCode = ErrorCode.ERROR
        this.httpStatus = httpStatus
        this.errorMessage = errorMessage
    }

    constructor(httpStatus: HttpStatus, errorMessage: String, errorData: String) : this() {
        this.errorCode = ErrorCode.ERROR
        this.httpStatus = httpStatus
        this.errorMessage = errorMessage
        this.errorData = errorData
    }

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace
    )
}