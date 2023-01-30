package kr.co.interiorkotlin

import io.github.kaiso.relmongo.config.EnableRelMongo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
@EnableTransactionManagement
@EnableRelMongo
@EnableAsync
@EnableFeignClients
@EnableScheduling
class InteriorKotlinApplication

fun main(args: Array<String>) {
    runApplication<InteriorKotlinApplication>(*args)
}
