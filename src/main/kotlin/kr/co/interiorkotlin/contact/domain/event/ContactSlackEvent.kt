package kr.co.interiorkotlin.contact.domain.event

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.contact.domain.Contact

@Schema(title = "견적 문의 Slack 알람", hidden = true)
data class ContactSlackEvent(

    @field:Schema(title = "견적 문의")
    val contact: Contact
) {


}