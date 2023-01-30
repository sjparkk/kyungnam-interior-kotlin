package kr.co.interiorkotlin.contact.repository

import kr.co.interiorkotlin.contact.domain.Contact
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ContactRepository: JpaRepository<Contact, Long> {

    fun findContactsByName(pageable: Pageable, name: String): PageImpl<Contact>

    fun findContactsByAddressLike(pageable: Pageable, address: String): PageImpl<Contact>

    fun findContactsByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Contact>
}