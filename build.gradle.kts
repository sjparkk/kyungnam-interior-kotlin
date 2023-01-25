plugins{

    id("org.springframework.boot") version "2.6.14"
    id("io.spring.dependency-management") version "1.1.0"

    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    //해당 작업을 통해 allopen baseEntity -> open 선언 안하고 open으로 사용할 수 있게 함
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion
}

//해당 작업을 통해 allopen baseEntity -> open 선언 안하고 open으로 사용할 수 있게 함
allOpen{
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")

    // mongodb
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

group= "com.kn"
version= "0.0.1-SNAPSHOT"
java.sourceCompatibility= JavaVersion.VERSION_11

repositories{
    mavenCentral()

    //komoran
    maven("https://jitpack.io")
}

kapt {
    annotationProcessor("com.querydsl.apt.jpa.JPAAnnotationProcessor")
    annotationProcessor("org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor")
}

dependencies{
    //spring-web, mysql, h2 db, spring-data-jpa, spring-data-jdbc, validation 추가한 기본 dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
    implementation("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    val hibernateTypes52Version = "2.12.1"
    //mysql
    implementation("com.vladmihalcea:hibernate-types-52:${hibernateTypes52Version}")

    //querydsl
    implementation("com.querydsl:querydsl-jpa")
    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa") // querydsl
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    // Querydsl 경로 설정
    sourceSets.main {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("$buildDir/generated/source/kapt/main")
        }
    }


    val p6spyVersion = "1.6.3"
    // JPA 로그 출력
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:${p6spyVersion}")

    //json
    implementation( "org.json:json:20210307")

    // GSON
    implementation("com.google.code.gson:gson:2.10")

    //kotest - 아래 두개 추가 필요
    val KOTEST_VERSION = "4.5.0"
    testImplementation("io.kotest:kotest-runner-junit5-jvm:${KOTEST_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${KOTEST_VERSION}")

    // Mockk
    testImplementation("io.mockk:mockk:1.12.0")

    val springdocVersion = "1.5.10"
    //Swagger OpenAPI 3.0
    // GitHub: https://github.com/springdoc/springdoc-openapi
    // DEMO: https://github.com/springdoc/springdoc-openapi-demos
    // Getting Started: https://springdoc.org
    implementation("org.springdoc:springdoc-openapi-ui:${springdocVersion}")
    implementation("org.springdoc:springdoc-openapi-data-rest:${springdocVersion}")

    //mongodb
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // https://kaiso.github.io/relmongo/
    // MongoDB 릴레이션 맵핑 라이브러리
    val relmongoVersion = "3.4.2"
    implementation("io.github.kaiso.relmongo:relmongo:${relmongoVersion}")

    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:31.1-jre")

    // Feign Client
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.5")

    // Komoran - https://mvnrepository.com/artifact/com.github.shin285/KOMORAN
    implementation("com.github.shin285:KOMORAN:3.3.4")

    // Jcseg-core - https://mvnrepository.com/artifact/org.lionsoul/jcseg-core
    implementation("org.lionsoul:jcseg-core:2.6.2")

    val kotlinxCoroutinesCoreVersion = "1.5.1"
    // 코루틴
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesCoreVersion")

    //apache commons
    val commonsLang3Version = "3.12.0"
    implementation("org.apache.commons:commons-lang3:$commonsLang3Version")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
    kotlinOptions{
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test>{
    useJUnitPlatform()
}