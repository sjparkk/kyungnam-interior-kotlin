package kr.co.interiorkotlin.common.utils

import kr.co.interiorkotlin.common.client.TagExtractionServiceClient
import kr.co.interiorkotlin.common.domain.dto.ReqExtractionTagDTO
import kr.co.interiorkotlin.common.domain.dto.ResExtractionTagDTO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TagExtractionServiceUtils(
    val tagExtractionServiceClient: TagExtractionServiceClient
) {
    //태그 추출 개수
    private val tagCount = 5

    //태그 추출 언어
    private val lang = "ko"

    //api token key
    private val xAuthToken = "61638a4f2301b1949832a3ef7e71a7f6"

    //retry count
    private val maxRetries = 3

    private val log = LoggerFactory.getLogger(javaClass)

    fun getExtractTag(title: String, contentText: String): List<String> {

        val req = ReqExtractionTagDTO(
            sentences = arrayOf(title+contentText),
            lang = lang,
            maxCandidateNum = tagCount
        )

        lateinit var result: ResExtractionTagDTO

        for(i in 0..maxRetries) {
            try {
                result = tagExtractionServiceClient.extractTag(xAuthToken, req)

                // TODO : logback..
                if(result.code >= HttpStatus.BAD_REQUEST.value() && result.code <= HttpStatus.TOO_MANY_REQUESTS.value()) {
                    log.debug(":: msg - ${result.msg}, code - ${result.code}")
                    continue
                } else {
                    break
                }
            } catch (e: Exception) {
                if(i == maxRetries) throw e
            }
        }

        //정렬 및 키워드만 추출
        val response = result.result.sortedByDescending { it.weight }.map { it.keyword }
        log.debug(":: 추출 키워드 - $response")

        return response

    }
}