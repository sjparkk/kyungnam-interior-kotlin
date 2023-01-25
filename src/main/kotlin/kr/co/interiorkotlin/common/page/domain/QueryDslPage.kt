package kr.co.interiorkotlin.common.page.domain

class QueryDslPage<T>(
    val results: MutableList<T>,
    val totalCount: Long
)