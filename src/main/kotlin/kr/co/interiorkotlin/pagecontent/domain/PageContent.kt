package kr.co.interiorkotlin.pagecontent.domain

import io.swagger.v3.oas.annotations.media.Schema
import kr.co.interiorkotlin.banner.domain.enums.BannerTypeEnum
import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import kr.co.interiorkotlin.pagecontent.domain.enums.PageContentTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Schema(title = "페이지 컨텐츠", description = "페이지에 대한 진열 사항을 관리")
@Document(collection = "page_content")
class PageContent(

    @field:MongoId
    val id: ObjectId? = null,

    @field:Schema(title = "기기 구분")
    val deviceType: DeviceTypeEnum,

    @field:Schema(title = "페이지 이름")
    val pageName: String,

    @field:Schema(title = "노출 위치 타입")
    val pageType: PageContentTypeEnum,

    @field:Schema(title = "배너 정보")
    val bannerType: BannerTypeEnum,

    @field:Schema(title = "인테리어 코드 정보")
    val interiorCodes: List<String>,

    )