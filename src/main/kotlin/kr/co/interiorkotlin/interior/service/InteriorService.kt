package kr.co.interiorkotlin.interior.service

import kr.co.interiorkotlin.banner.domain.dto.BannerInfoDTO
import kr.co.interiorkotlin.banner.repository.BannerRepository
import kr.co.interiorkotlin.common.domain.enums.DeviceTypeEnum
import kr.co.interiorkotlin.common.exception.DataNotFoundException
import kr.co.interiorkotlin.common.page.domain.ResPageDTO
import kr.co.interiorkotlin.common.utils.TagExtractionCustomUtils
import kr.co.interiorkotlin.interior.domain.dto.InteriorInfoDTO
import kr.co.interiorkotlin.interior.domain.dto.InteriorSearchDTO
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorDetailDTO
import kr.co.interiorkotlin.interior.domain.dto.res.ResInteriorInitDTO
import kr.co.interiorkotlin.interior.repository.InteriorRepository
import kr.co.interiorkotlin.pagecontent.domain.enums.PageContentTypeEnum
import kr.co.interiorkotlin.pagecontent.repository.PageContentRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class InteriorService(
    val interiorRepository: InteriorRepository,
    val bannerRepository: BannerRepository,
    val pageContentRepository: PageContentRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 메인 페이지 초기 정보 조회 (베너 및 인테리어 작업물 정보)
     * @param deviceType DeviceTypeEnum
     * @return ResInteriorInitDTO
     */
    fun initInterior(deviceType: DeviceTypeEnum): ResInteriorInitDTO {

        //페이지컨텐츠 메인 조회
        val findPageContent = pageContentRepository.findOneByDeviceTypeAndPageType(deviceType, PageContentTypeEnum.MAIN)

        //페이지컨텐츠에 설정 된 베너 정보와 인테리어 정보 조회
        val findBanners = bannerRepository.findTop3ByPositionTypeOrderByOrderAsc(findPageContent.bannerType.name)
        val findInteriors = interiorRepository.findInteriorsByInteriorCodeIn(findPageContent.interiorCodes)

        //TagExtractionCustomUtils 를 통해 태그 정보 추출 후 반환
        return ResInteriorInitDTO(
            bannerInfo = BannerInfoDTO(imageUrl = findBanners.map { it.imageUrl }),
            interiorInfo = findInteriors.map {
                InteriorInfoDTO(
                    interiorCode = it.interiorCode,
                    title = it.title,
                    thumbnailUrl = it.thumbnailUrl,
                    tags = TagExtractionCustomUtils.extractAllTag(it.title!!, it.content!!),
                    id = it.id
                )
            }
        )
    }


    /**
     * 인테리어 작업물 리스트 조회
     * @param pageable Pageable
     * @param req ReqInteriorSearchDTO
     * @return ResPageDTO<InteriorSearchDTO>
     */
    fun getInteriorList(pageable: Pageable, space: Int?, interiorType: String?): ResPageDTO<InteriorSearchDTO> {

        //인테리어 조회
        val response = interiorRepository.findInteriorBySpaceAndType(pageable, space, interiorType)

        //태그 정보 추출
        response.forEach {
            it.tags = TagExtractionCustomUtils.extractAllTag(it.title!!, it.content!!)
        }

        return ResPageDTO(response)

    }

    /**
     * 인테리어 상세 정보 조회
     * @param interiorCode
     * @return
     */
    fun getInterior(interiorCode: String): ResInteriorDetailDTO {
        val findInterior = interiorRepository.findByInteriorCode(interiorCode)
            ?: throw DataNotFoundException("존재하지 않는 작업물 입니다.")

        return ResInteriorDetailDTO(
            interiorCode = findInterior.interiorCode,
            title = findInterior.title,
            imageUrl = findInterior.imageUrl,
            thumbnailUrl = findInterior.thumbnailUrl,
            interiorType = findInterior.interiorType,
            space = findInterior.space,
            address = findInterior.address,
            tags = TagExtractionCustomUtils.extractAllTag(findInterior.title!!, findInterior.content!!),
            interiorDetail = findInterior.interiorDetail
        )


    }

}