package kr.co.interiorkotlin.common.client

import kr.co.interiorkotlin.common.config.FeignLoggingConfig
import kr.co.interiorkotlin.common.domain.dto.ReqExtractionTagDTO
import kr.co.interiorkotlin.common.domain.dto.ResExtractionTagDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "kafka-api", url = "\${feign.target-api.kakao-nlp}", configuration = [FeignLoggingConfig::class])
interface TagExtractionServiceClient {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun extractTag(
        @RequestHeader(value = "x-api-key") xAuthToken: String,
        @RequestBody req: ReqExtractionTagDTO
    ): ResExtractionTagDTO
}