package kr.co.interiorkotlin.common.utils

import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import java.util.*


class TagExtractionCustomUtilsTest: StringSpec({

    "extractKoreanTag 를 이용한 키워드 추출" {
        val komoran = Komoran(DEFAULT_MODEL.FULL)

        val resultMap = mutableMapOf<String, Int>()

        val target = """
            여기서 주의할 점은, 이 모듈을 의존하는 다른 모듈에서도 data jpa 의존성을 
            사용하고 싶은 경우 implementation 이 아닌 api 를 사용해야 한다는 점이다. 
            나는 다른 implementation implementation implementation 모듈에서도 의존성이 
            필요한 경우, 해당 모듈에서도 필요한 의존성을 implementation 하는 게 적절하다고 
            생각되어 implementation 을 선택했다. root project 에는 필요하지 않은 플러그인이므로,
            apply false 를 작성한다. jvm 플러그인은 적용한다. kotlin 관련 버전 정보는 여러 
            플러그인에 공통으로 사용되므로 gradle.properties파일 및 settings.gradle.kts 파일에 
            별도로 작성해 공통으로 사용한다.
        """.trimIndent()

        //특문 & 공백 제거
        val replaceTarget = target.replace(Regex("[^가-힣a-zA-Z0-9]"), " ").trim()

        //명사 단어만 추출
        var analyzeResults = komoran.analyze(replaceTarget).nouns
        println(analyzeResults.toString())

        //중복 제거
        val resultSet = analyzeResults.filter { it.length > 1 }.toSet()
        println(resultSet.toString())

        //빈도수
        resultSet.forEach {
            val frequency = Collections.frequency(analyzeResults, it)
            resultMap[it] = frequency
        }

        //정렬 후 갯수 제한
        var descendingResult = resultMap.toList().sortedByDescending { it.second }
        if(descendingResult.size > 5) {
            descendingResult = descendingResult.subList(0, 5)
        }

        println(descendingResult.joinToString(" ") { it.first })
    }

    "extractKoreanTag" {
        val title = "일반인 시절 연예인 보다 예뻐서 연예계 데뷔한 여대생 근황"

        val content = """
           김미경-유재명-황승언-이서환-김하영-정형석 ‘서프라이즈~’한 캐스팅 화제! 2023년 새해가 밝자 마자 해피 무비로 관객들의 사랑을 받고 있는 <스위치>. 명품 배우들의 다채로운 매력이 화제다. 베테랑 배우들과 씬스틸러, 그리고 마이너 문화의 급부상으로 깜짝 스타가된 배우들 까지... 명실공히 명배우 김미경, 존재감 끝판왕 유재명, 도도한 매력의 황승언, 씬스틸러 이서환, ‘서프라이즈~’의 김하영 & 정형석까지. 이들은 각각의 매력을 뽐내며 <스위치>에 활력을 불어 넣고 있다. <스위치>는 천만배우로 자타공인 스캔들 메이커로 충무로 캐스팅 0순위로 꼽히는 배우이자 화려한 싱글 라이프를 만끽하던 톱스타 ‘박강’이 갑자기 인생이 뒤바뀌는 크리스마스를 맞는 이야기이다. 이영화에서 ‘박강’(권상우)의 엄마로 출연해 눈길을 모으는 베테랑 김미영은 영화 <82년생 김지영>과 드라마 [상속자들], [또 오해영], [사이코지만 괜찮아] 등 출연하는 작품마다 관객들에게 깊은 인상을 주었다. 그녀는 <스위치>에 합류해 편안한 노후를 즐기며 명품 쇼핑, 크루즈 여행이나 하다 뒤바뀐 세상 속에서 아들 걱정에 온종일 잔소리를 달고 사는 두가지 성격의 역할을 맛깔나게 표현해 낸다. 유재명은 또 누군가, 영화 <소리도 없이><킹메이커>, 드라마 [비밀의 숲], [이태원 클라쓰] 등에서 소름돋는 탁월한 연기로 엄청난 캐릭터를 구축하는 능력을 가진 배우다. 그의 넓은 연기 스펙트럼은 ‘박강’이 탄 택시의 기사 역으로 우정출연에도 불구, 중후한 음색과 미스터리한 분위기로 강렬한 존재감을 내 뿜는다. 도시적 매력의 황승언도 빠질 수 없다. 일반인 시절 과거 한 예능 프로그램에 연예인 친구로 출연한것을 계기로, 연예계에 정식 데뷔했던 그녀는 이후 영화 <족구왕><더 킹>, 드라마 [내가 가장 예뻤을 때], [결혼백서] 등 영화, 드라마, OTT를 넘나들며 활약 중인 배우가 되었다. 황승언은 극 중 ‘박강’의 N번째 썸녀로 특별출연해 도도한 매력을 한 껏 뽐낸다. 여기에 연극, 뮤지컬, 드라마, 영화 등 활발하게 활동 중인 배우 이서환이 감독역으로 출연한다. 톱스타 ‘박강’ 때문에 늘 마음 고생을 하다 뒤바뀐 세상에서는 무명배우 ‘박강’ 때문에 한숨 돌리는 드라마 감독 역을 개성 넘치는 연기로 소화해 냈다. 그의 진짜 감독 같은 리얼 연기는 권상우, 오정세와 완벽한 케미를 이루며 씬스틸러 역할을 톡톡히 해냈다. 무엇보다 반가운 얼굴이 있다. 하루아침에 무명배우가 된 ‘박강’이 생계를 위해 출연 하는 [신비한TV 서프라이즈]의 동료 연기자로 실제 [신비한TV 서프라이즈]의 대표 배우 김하영이 ‘박강’의 선배 연기자로 나와 익살맞은 장면을 연출한다. 교양 프로그램 [나는 자연인이다]의 내레이션으로 유명한 성우 겸 배우 정형석이 드라마 연출자로 등장해 실감 나는 연기로 극의 재미를 배가시킨다. 스크린에 활력을 불어넣는 명 배우들의 활약으로 화제를 모으는 영화 <스위치>는 전국 극장에서 절찬 상영 중이다. 스위치 감독 마대윤 출연 권상우, 오정세, 이민정, 박소이, 김준, 김미경 평점 7.2 damovie2019@gmail.com(오타 신고/제보 및 보도자료) ※저작권자 ⓒ 필 더 무비. 무단 전재-재배포 금지
        """.trimIndent()

        val result = TagExtractionCustomUtils.extractKoreanTag(title, content)
        println(result.toString())
    }


    "extractEnglishTag 를 이용한 키워드 추출" {
        val title = "나는 제목이다"

        val content = """
            여기서 주의할 점은, 이 모듈을 의존하는 다른 모듈에서도 data jpa 의존성을 
            사용하고 싶은 경우 implementation 이 아닌 api 를 사용해야 한다는 점이다. 
            나는 다른 implementation implementation implementation 모듈에서도 의존성이 
            필요한 경우, 해당 모듈에서도 필요한 의존성을 implementation 하는 게 적절하다고 
            생각되어 implementation 을 선택했다. root project 에는 필요하지 않은 플러그인이므로,
            apply false 를 작성한다. jvm 플러그인은 적용한다. kotlin 관련 버전 정보는 여러 
            플러그인에 공통으로 사용되므로 gradle.properties파일 및 settings.gradle.kts 파일에 
            별도로 작성해 공통으로 사용한다.
        """.trimIndent()

        val result = TagExtractionCustomUtils.extractEnglishTag(title, content)
        println(result.toString())
    }

    "최종 추출된 5개 키워드 태그 정보" {
        val title = "spring jpa 를 사용할 예정이므로 jpa 관련 플러그인을 적용해 준다. 그외 다른 플러그인은 root project 에서 공통으로 설정할 예정이다. 플러그인 버전은 root project 에서 작성한 버전을 따라간다.tire-domain모듈에서 사용할 spring data jpa 관련dependencies를 작성한다. (root 프로젝트에 작성해 둔dependencies들을 복사해서 들고오면 편하다.)"

        val content = """
                    여기서 주의할 점은, 이 모듈을 의존하는 다른 모듈에서도 data jpa 의존성을 
                    사용하고 싶은 경우 implementation 이 아닌 api 를 사용해야 한다는 점이다. 
                    나는 다른 implementation implementation implementation 모듈에서도 의존성이 
                    필요한 경우, 해당 모듈에서도 필요한 의존성을 implementation 하는 게 적절하다고 
                    생각되어 implementation 을 선택했다. root project 에는 필요하지 않은 플러그인이므로,
                    apply false 를 작성한다. jvm 플러그인은 적용한다. kotlin 관련 버전 정보는 여러 
                    플러그인에 공통으로 사용되므로 gradle.properties파일 및 settings.gradle.kts 파일에 
                    별도로 작성해 공통으로 사용한다.
        """.trimIndent()


        val koTag: List<Pair<String, Int>>
        val enTag: List<Pair<String, Int>>

        runBlocking {
            val koTagAsync = async(Dispatchers.IO) {
                TagExtractionCustomUtils.extractKoreanTag(title, content)
            }
            val enTagAsync = async(Dispatchers.IO) {
                TagExtractionCustomUtils.extractEnglishTag(title, content)
            }

            koTag = koTagAsync.await()
            enTag = enTagAsync.await()
        }

        println("::koTag - $koTag")
        println("::enTag - $enTag")

        val result = TagExtractionCustomUtils.getTagsByTagCount(koTag, enTag)
        println("::result - $result")
    }

})