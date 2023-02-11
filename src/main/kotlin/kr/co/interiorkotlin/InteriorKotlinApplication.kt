package kr.co.interiorkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableTransactionManagement
@EnableAsync
@EnableFeignClients
@EnableScheduling
class InteriorKotlinApplication

fun main(args: Array<String>) {
    runApplication<InteriorKotlinApplication>(*args)
}
