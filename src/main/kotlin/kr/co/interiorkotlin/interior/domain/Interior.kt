package kr.co.interiorkotlin.interior.domain

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.domain.BaseIncludeDeleteEntity
import kr.co.interiorkotlin.common.generator.CodeGenerator
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Schema(title = "인테리어 테이블")
@Entity
@Table(name = "interior.interior")
@DynamicInsert
@DynamicUpdate
class Interior(

    @field:Schema(title = "인테리어 고유 코드")
    val interiorCode: String? = CodeGenerator.RANDOM("IN"),

    @field:Schema(title = "제목")
    val title: String? = null,

    @field:Schema(title = "내용")
    val content: String? = null,

    @field:Schema(title = "이미지 url")
    val imageUrl: String? = null,

    @field:Schema(title = "썸네일 url")
    val thumbnailUrl: String? = null,

    @field:Schema(title = "인테리어 타입", description = "아파트/빌라/주택/상가/사무실")
    val interiorType: String? = null,

    @field:Schema(title = "평수")
    val space: Int? = null,

    @field:Schema(title = "주소")
    val address: String? = null,

    @OneToMany
    @JoinColumn(name = "interiorId")
    @field:Schema(title = "인테리어 상세 정보")
    val interiorDetail: MutableList<InteriorDetail>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(title = "인테리어 ID")
    val id: Long? = null,

    ): BaseIncludeDeleteEntity() {

}

