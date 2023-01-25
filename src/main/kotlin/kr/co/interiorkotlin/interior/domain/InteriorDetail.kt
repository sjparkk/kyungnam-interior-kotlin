package kr.co.interiorkotlin.interior.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Schema(title = "인테리어 상세 테이블")
@Entity
@Table(name = "interior.interior_detail")
@DynamicInsert
@DynamicUpdate
class InteriorDetail(

    @field:Schema(title = "이미지 url")
    val imageUrl: String? = null,

    @field:Schema(title = "태그 정보")
    val tags: String? = null,

    @field:Schema(title = "인테리어 아이디")
    val interiorId: Long? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(title = "인테리어 ID")
    val id: Long? = null,
) {


}

