package kr.co.interiorkotlin.contact.service

import kr.co.interiorkotlin.common.delegation.MaskingName
import kr.co.interiorkotlin.common.exception.DataNotFoundException
import kr.co.interiorkotlin.common.exception.ErrorInValidParamException
import kr.co.interiorkotlin.common.generator.PasswordGenerator
import kr.co.interiorkotlin.common.page.domain.ResPageDTO
import kr.co.interiorkotlin.contact.domain.Contact
import kr.co.interiorkotlin.contact.domain.dto.ContactDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSaveDTO
import kr.co.interiorkotlin.contact.domain.dto.req.ReqContactSearchDTO
import kr.co.interiorkotlin.contact.domain.dto.res.ResContactDetailDTO
import kr.co.interiorkotlin.contact.domain.enums.SearchTypeEnum
import kr.co.interiorkotlin.contact.domain.event.ContactSlackEvent
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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.lang.String.format
import java.security.MessageDigest

@Service
class ContactService(
    private val contactRepository: ContactRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 견적 문의 생성 및 슬랙 알람
     * @param req ReqContactSaveDTO
     */
    @Transactional
    fun saveContact(req: ReqContactSaveDTO) {

        val (hashPassword, salt) = PasswordGenerator.hashing(req.password)
        log.debug(":: 암호화 된 password - $hashPassword, 기존 암호 - ${req.password}")

        val contact = Contact()
        BeanUtils.copyProperties(req, contact)
        val saveContact = contactRepository.save(contact)
        saveContact.hashing(hashPassword, salt)

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
        val hashedPassword = hashingPassword(password, contact.salt!!)
        if(hashedPassword != contact.password) throw ErrorInValidParamException("비밀번호가 같지 않습니다")

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

    private fun hashingPassword(password: String, salt: String): String {
        // SHA-256 해시함수를 사용
        val md = MessageDigest.getInstance("SHA-256")

        // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
        val temp = password + salt

        // temp 의 문자열을 해싱하여 md 에 저장해둔다
        md.update(temp.toByteArray())

        // md 객체의 다이제스트를 얻어 password 를 갱신한다
        val transPwd = md.digest()

        // 바이트 값을 16진수로 변경
        val sb = StringBuilder()
        for (a in transPwd) {
            sb.append(String.format("%02x", a))
        }
        return sb.toString()

    }

}