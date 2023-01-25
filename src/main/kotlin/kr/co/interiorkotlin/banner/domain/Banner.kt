package kr.co.interiorkotlin.banner.domain

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.domain.BaseIncludeDeleteEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Schema(title = "베너")
@Entity
@Table(name = "interior.banner")
@DynamicInsert
@DynamicUpdate
class Banner(

    @field:Schema(title = "베너 노출 위치 타입", description = """
       MAIN - 메인 페이지
       DETAIL - 인테리어 상세 페이지
       CONTACT - 견적 문의 페이지 
    """)
    val positionType: String? = null,

    @field:Schema(title = "이미지 url")
    val imageUrl: String,

    @field:Schema(title = "노출 순서")
    val order: Int? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(title = "인테리어 ID")
    val id: Long? = null,

): BaseIncludeDeleteEntity() {

    override fun toString(): String {
        return "Banner(positionType=$positionType, imageUrl='$imageUrl', order=$order, id=$id)"
    }
}

