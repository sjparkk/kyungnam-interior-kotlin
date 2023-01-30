package kr.co.interiorkotlin.contact.domain.event

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "스케줄러 견적 문의 푸쉬 Slack 알람", hidden = true)
data class ScheduledContactPushSlackEvent(
    @field:Schema(title = "알람 견적 문의 건수")
    val count: Int,
    @field:Schema(title = "현재 시간")
    val hour: Int,
)