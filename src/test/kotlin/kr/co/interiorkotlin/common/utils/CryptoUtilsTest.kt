package kr.co.interiorkotlin.common.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CryptoUtilsTest(
    val cryptoUtils: CryptoUtils
) {

    val plainText = "password1234"

    @Test
    fun `암복호화`() {

        println("plainText :: $plainText")

        val encryptedText = cryptoUtils.encryptAES(plainText)
        println("encryptoText :: $encryptedText")

        val decryptedText = cryptoUtils.decryptAES(encryptedText)
        println("decryptedText :: $decryptedText")

        Assertions.assertEquals(plainText, decryptedText)
    }

    @Test
    fun `복호화`() {
        val encryptedText = "jwQPM2gjGYO/lqnl4N4dBWioBlh/eLdYqnk="
        println("encryptedText :: $encryptedText")

        val decryptedText = cryptoUtils.decryptAES(encryptedText)
        println("decryptedText :: $decryptedText")

        Assertions.assertEquals("test121212", decryptedText)
    }

}