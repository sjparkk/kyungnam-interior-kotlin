package kr.co.interiorkotlin.pagecontent.domain.enums

enum class PageContentTypeEnum (
    val desc: String
) {
    MAIN("메인"),
    DETAIL("상세"),
    CONTACT("견적문의"),
    QNA("문의"),
    FAQ("자주묻는질문"),
    NOTICE("공지사항"),
}