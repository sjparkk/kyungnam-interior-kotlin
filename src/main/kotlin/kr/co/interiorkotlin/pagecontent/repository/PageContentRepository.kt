package kr.co.interiorkotlin.pagecontent.repository

import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import kr.co.interiorkotlin.pagecontent.domain.PageContent
import kr.co.interiorkotlin.pagecontent.domain.enums.PageContentTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PageContentRepository: MongoRepository<PageContent, ObjectId> {

    fun findOneByDeviceTypeAndPageType(deviceTypeEnum: DeviceTypeEnum, pageType: PageContentTypeEnum): PageContent
}