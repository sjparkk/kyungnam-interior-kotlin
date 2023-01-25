package kr.co.interiorkotlin.common.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@Schema(hidden = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseIncludeDeleteEntity(
    @field:Schema(title = "삭제여부: 미삭제(0), 삭제(1)")
    var deleteFlag: Boolean? = null,

    @field:Schema(title = "삭제일시")
    var deletedAt: LocalDateTime? = null
) : BaseEntity() {

    fun delete() {
        this.deleteFlag = true
        this.deletedAt = LocalDateTime.now()
    }

}