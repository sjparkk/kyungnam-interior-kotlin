package kr.co.interiorkotlin.contact.domain

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.common.domain.BaseIncludeDeleteEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Schema(title = "견적 문의 테이블")
@Entity
@Table(name = "interior.contact")
@DynamicInsert
@DynamicUpdate
class Contact() : BaseIncludeDeleteEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(title = "인테리어 ID")
    val id: Long? = null

    @field:Schema(title = "인테리어 타입", description = "아파트/빌라/상가/주택/사무실")
    var interiorType: String? = null

    @field:Schema(title = "고객명")
    var name: String? = null

    @field:Schema(title = "번호")
    var phone: String? = null

    @field:Schema(title = "이메일")
    var email: String? = null

    @field:Schema(title = "우편번호")
    var postCode: String? = null

    @field:Schema(title = "주소")
    var address: String? = null

    @field:Schema(title = "상세주소")
    var addressDetail: String? = null

    @field:Schema(title = "방개수")
    var roomCnt: Int? = null

    @field:Schema(title = "화장실개수")
    var bathroomCnt: Int? = null

    @field:Schema(title = "평수 or 면적")
    var space: Int? = null

    @field:Schema(title = "타입에 따라 평수 or 면적 -> 면적일시 평수로 변환해주는 로직")
    var spaceType: String? = null

    @field:Schema(title = "예산")
    var budget: String? = null

    @field:Schema(title = "통화 가능 시간")
    var callTime: String? = null

    @field:Schema(title = "게시글 비밀번호")
    var password: String? = null

    @field:Schema(title = "문의내용")
    var content: String? = null

    @field:Schema(title = "접근 경로")
    var accessRoute: String? = null

    override fun toString(): String {
        return "Contact(id=$id, interiorType=$interiorType, name=$name, phone=$phone, email=$email, postCode=$postCode, address=$address, addressDetail=$addressDetail, roomCnt=$roomCnt, bathroomCnt=$bathroomCnt, space=$space, spaceType=$spaceType, budget=$budget, callTime=$callTime, password=$password, content=$content, accessRoute=$accessRoute)"
    }

}



