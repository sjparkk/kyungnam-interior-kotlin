package kr.co.interiorkotlin.common.generator

import java.security.MessageDigest
import java.security.SecureRandom

class PasswordGenerator {

    companion object {

        private const val SALT_SIZE = 16

        fun hashing(password: String): Pair<String, String> {

            val rnd = SecureRandom()
            val tempSalt = ByteArray(SALT_SIZE)
            rnd.nextBytes(tempSalt)
            val salt = byteToString(tempSalt)

            val msgDigest = MessageDigest.getInstance("SHA-256") // SHA-256 해시함수를 사용
            val temp = password + salt
            msgDigest.update(temp.toByteArray()) // temp 의 문자열을 해싱하여 md 에 저장해둔다
            val transPwd: ByteArray = msgDigest.digest() // md 객체의 다이제스트를 얻어 password 를 갱신한다
            return Pair(byteToString(transPwd), salt)
        }

        private fun byteToString(temp: ByteArray): String {
            val sb = StringBuilder()
            for (a in temp) {
                sb.append(String.format("%02x", a))
            }
            return sb.toString()
        }
    }
}