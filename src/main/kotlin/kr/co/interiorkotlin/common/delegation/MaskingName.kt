package kr.co.interiorkotlin.common.delegation

import java.util.regex.Pattern
import kotlin.reflect.KProperty

class MaskingName(
    var name: String
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return replaceStr()
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        name = value
    }

    override fun toString(): String {
        return replaceStr()
    }

    private fun replaceStr(): String {
        val isEnglish = Pattern.matches("^[a-zA-Z\\s]*$", name) //영문 공백 조합인 이름에 대해서는 별도 예외처리가 필요하여 변경처리진행
        return if (isEnglish) {
            when (name.length) {
                1, 2 -> name
                3 -> name.replace("(?<=^..)(.*)".toRegex(), "*")
                else -> name.replace("(?<=^...)(.*)".toRegex(), "*")
            }
        } else {
            when (name.length) {
                1 -> name
                2 -> name.replace("(?<=^.)(.*)".toRegex(), "*")
                else -> name.replace("(?<!^).(?!\$)".toRegex(), "*")
            }
        }
    }

}