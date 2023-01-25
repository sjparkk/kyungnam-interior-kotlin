package kr.co.interiorkotlin.banner.domain.enums

enum class BannerTypeEnum(
    val desc: String
) {
    MAIN("메인"),
    DETAIL("상세"),
    CONTACT("견적문의"),

    //TODO : 추후 추가 예정
//    QNA("문의"),
//    FAQ("자주묻는질문"),
//    NOTICE("공지사항"),
}