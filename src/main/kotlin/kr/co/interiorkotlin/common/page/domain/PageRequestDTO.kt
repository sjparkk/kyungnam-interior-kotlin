package kr.co.interiorkotlin.common.page.domain

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@ParameterObject
class PageRequestDTO(
    @field:Parameter(description = "page 기본값", hidden = true)
    val defaultPage: Int = 0,
    @field:Parameter(description = "size 기본값", hidden = true)
    val defaultSize: Int = 20,

    @field:Parameter(name = "page", description = "## 조회할 페이지 번호\n", example = "0")
    var page: Int? = null,
    @field:Parameter(name = "size", description = "## 한페이지당 데이터 ROW 수\n"+
            "`size` 에 따라서 전체 페이지수가 달라짐\n" +
            "기본값은 `20`, 보내지 않으면 한 페이지 당 `20` 데이터를 응답함", example = "20")
    var size: Int? = null,
) {
    private val ORDER_DESC = "DESC"
    private val ORDER_ASC = "ASC"

    @field:Parameter(name = "sort", description = "## 정렬\n" +
            "- 아이디로 오름차순 정렬 - `id,asc`\n" +
            "- 아이디로 내림차순 정렬 - `id,desc`\n"
        , example = "createdAt,desc")
    var sort: String? = null

    /**
     * 기본값 Page 커스텀 변경
     * Page 요청 값이 있을 경우 기본값 무시
     * @param page Int
     * @return PageRequestDTO
     */
    fun defaultPage(page: Int): PageRequestDTO {
        if(this.page == null)
            this.page = page
        return this
    }

    /**
     * 기본값 Size 커스텀 변경
     * Size 요청 값이 있을 경우 기본값 무시
     * @param size Int
     * @return PageRequestDTO
     */
    fun defaultSize(size: Int): PageRequestDTO {
        if(this.size == null)
            this.size = size
        return this
    }

    /**
     * 기본값 Sort 커스텀 변경
     * Sort 요청 값이 있을 경우 기본값 무시
     * @param sort String
     * @return PageRequestDTO
     */
    fun defaultSort(sort: String): PageRequestDTO {
        if(this.sort == null)
            this.sort = sort
        return this
    }

    /**
     * Pageable 객체 생성
     * @param page Int
     * @param size Int
     * @param sort String?
     * @return Pageable
     */
    fun of(page: Int, size: Int, sort: String?): Pageable {
        if(sort.isNullOrEmpty())
            return PageRequest.of(page, size)
        return PageRequest.of(page, size, sortBy(sort))
    }

    /**
     * sort by 생성
     * @param sort String
     * @return Sort
     */
    private fun sortBy(sort: String): Sort {
        val sorts = sort.split(",")
        if (sorts.size < 2)
            return Sort.unsorted()

        val sort = sorts[0]
        return when (sorts[1].uppercase()) {
            ORDER_DESC -> Sort.by(sort).descending()
            ORDER_ASC -> Sort.by(sort).ascending()
            else -> Sort.unsorted()
        }
    }

    /**
     * 요청한 Pageable 변수로 Pageable 객체 생성
     * @return Pageable
     */
    fun getPageable(): Pageable =
        of(this.page ?: defaultPage, this.size ?: defaultSize, this.sort)

}