package kr.co.interiorkotlin.contact.service

import kr.co.interiorkotlin.common.delegation.MaskingName
import kr.co.interiorkotlin.common.exception.DataNotFoundException
import kr.co.interiorkotlin.common.exception.ErrorInValidParamException
import kr.co.interiorkotlin.common.page.domain.ResPageDTO
import kr.co.interiorkotlin.common.utils.CryptoUtils
import kr.co.interiorkotlin.contact.domain.Contact
import kr.co.interiorkotlin.contact.domain.dto.ContactDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSaveDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSearchDTO
import kr.co.interiorkotlin.contact.domain.dto.res.ResContactDetailDTO
import kr.co.interiorkotlin.contact.domain.enums.SearchTypeEnum
import kr.co.interiorkotlin.contact.domain.event.ContactSlackEvent
import kr.co.interiorkotlin.contact.domain.event.ScheduledContactPushSlackEvent
import kr.co.interiorkotlin.contact.repository.ContactRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.lang.String.format
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ContactService(
    private val contactRepository: ContactRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val cryptoUtils: CryptoUtils
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 견적 문의 생성 및 슬랙 알람
     * @param req ReqContactSaveDTO
     * @param file MultipartFile
     */
    @Transactional
    fun saveContact(
        req: ReqContactSaveDTO,
        file: MultipartFile?
    ) {

        val encryptedPassword = cryptoUtils.encryptAES(req.password)
        log.debug(":: 암호화 된 password - $encryptedPassword, 기존 암호 - ${req.password}")

        val contact = Contact()
        BeanUtils.copyProperties(req, contact)

        file?.let {
            val imageData: ByteArray = it.bytes
            contact.image = imageData
        }

        val saveContact = contactRepository.save(contact)
        saveContact.password = encryptedPassword

        //TODO : 추후 관리자 여러명인 경우 알람 추가 필요
        //신규 견적 문의 슬렉 알람
        eventPublisher.publishEvent(ContactSlackEvent(saveContact))
    }

    /**
     * 견적 문의 리스트 조회
     * @param pageable Pageable
     * @param req ReqContactSearchDTO
     * @return ResPageDTO<Contact>
     */
    fun getContactList(pageable: Pageable, req: ReqContactSearchDTO): ResPageDTO<ContactDTO> {

        val contacts = when(req.type?.uppercase()) {
            SearchTypeEnum.ADDRESS.name -> contactRepository.findContactsByAddressLike(pageable, req.keyword!!)
            SearchTypeEnum.NAME.name -> contactRepository.findContactsByName(pageable, req.keyword!!)
            else -> contactRepository.findAll(pageable)
        }

        val response = contacts.map {
            //고객명 마스킹
            val maskingUserName: String by MaskingName(it.name!!)

            ContactDTO(
                interiorType = it.interiorType,
                name = maskingUserName,
                phone = it.phone,
                email = it.email,
                postCode = it.postCode,
                address = it.address,
                addressDetail = it.addressDetail,
                roomCnt = it.roomCnt,
                bathroomCnt = it.bathroomCnt,
                space = it.space,
                spaceType = it.spaceType,
                budget = it.budget,
                callTime = it.callTime,
                content = it.content,
                accessRoute = it.accessRoute
            )
        }

        return ResPageDTO(response)
    }

    @Async
    @EventListener
    fun sendSlackContact(event: ContactSlackEvent) {

        val contact = event.contact

        val restTemplate = RestTemplate()
        val request: MutableMap<String, Any> = HashMap()
        request["username"] = "문의알림"
        val text = format(
            """
                견적문의가 들어왔습니다.
                `고객명`: %s
                `연락처`: %s
                `이메일`: %s
                `공사현장주소`: %s %s
                `건물구분`: %s
                `방 & 화장실`: %d, %d
                `예산`: %s
                `통화가능시간`: %s
                `문의사항`: %s
                `접근경로`: %s
                
                """.trimIndent(),
            contact.name, contact.phone,
            contact.email, contact.address, contact.addressDetail,
            contact.roomCnt, contact.bathroomCnt,
            contact.budget, contact.callTime,
            contact.content, contact.accessRoute
        )
        request["text"] = text

        val entity = HttpEntity<Map<String, Any>>(request)
        val url =
            "https://hooks.slack.com/services/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" // 사용할 슬랙의 Webhook URL 넣기

        // 경남인테리어 URL : https://hooks.slack.com/services/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
        restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java)

    }

    /**
     * 견적 문의 상세 조회
     * @param contactId
     * @param password
     * @return
     */
    fun getContact(contactId: Long, password: String): ResContactDetailDTO {

        val contact = contactRepository.findByIdOrNull(contactId)
            ?: throw DataNotFoundException("존재하지 않는 견적 문의입니다.")

        //비밀번호 확인
        val plainPassword = contact.password?.let { cryptoUtils.decryptAES(it) }
        if(plainPassword != null && plainPassword != password) throw ErrorInValidParamException("비밀번호가 같지 않습니다")

        return ResContactDetailDTO(
            interiorType = contact.interiorType,
            name = contact.name,
            phone = contact.phone,
            email = contact.email,
            postCode = contact.postCode,
            address = contact.address,
            addressDetail = contact.addressDetail,
            roomCnt = contact.roomCnt,
            bathroomCnt = contact.bathroomCnt,
            space = contact.space,
            spaceType = contact.spaceType,
            budget = contact.budget,
            callTime = contact.callTime,
            content = contact.content,
            accessRoute = contact.accessRoute
        )
    }

    /**
     * Cron contact push scheduler
     * 지정 된 시간에 견적 문의 알람을 푸시하는 스케줄러 (금일 신규 문의에 대해서만)
     * 업무 시작 전 알림 - 10시
     * 중간 알림 - 15시
     * 업무 종료 전 알림 - 19시
     */
//    @Scheduled(cron = "0 0 10,15,19 * * *")
    @Scheduled(cron = "0/60 * * * * *")
    fun cronContactPushScheduler() {

        val nowDate = LocalDate.now()
        val start = nowDate.atTime(0, 0, 0)
        val end = LocalDateTime.now()

        val result = contactRepository.findContactsByCreatedAtBetween(start, end)

        if(result.isNotEmpty())
            eventPublisher.publishEvent(ScheduledContactPushSlackEvent(result.size, end.hour))

    }

    @Async
    @EventListener
    fun sendSlackScheduledPushContact(event: ScheduledContactPushSlackEvent) {

        val count = event.count
        val hour = event.hour

        val restTemplate = RestTemplate()
        val request: MutableMap<String, Any> = HashMap()
        request["username"] = "견적 문의 금일 신규 문의 알림"
        val text = format(
            """
                금일 %d시 기준 %d 건의 견적 문의가 들어왔습니다.
                문의를 확인 해주세요. 
                """.trimIndent(),
            hour, count
        )
        request["text"] = text

        val entity = HttpEntity<Map<String, Any>>(request)
        val url =
            "https://hooks.slack.com/services/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" // 사용할 슬랙의 Webhook URL 넣기

        // 경남인테리어 URL : https://hooks.slack.com/services/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
        restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java)
    }

}