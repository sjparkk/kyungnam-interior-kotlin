package kr.co.interiorkotlin.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import org.lionsoul.jcseg.ISegment
import org.lionsoul.jcseg.IWord
import org.lionsoul.jcseg.dic.DictionaryFactory
import org.lionsoul.jcseg.segmenter.SegmenterConfig
import java.io.StringReader
import java.util.*

/**
 * 태그(키워드)와 관련된 공통 Utils Class
 */
class TagExtractionCustomUtils {

    companion object {

        // TODO : 추후 TABLE
        private const val TAG_COUNT = 5

        /**
         * 국문 영문 태그 추출 & 빈도수 TOP5 반환
         * @param title
         * @param content
         * @return
         */
        fun extractAllTag(title: String, content: String): List<String> {
            val koTag: List<Pair<String, Int>>
            val enTag: List<Pair<String, Int>>

            runBlocking {
                //국문 태그 추출
                val koTagAsync = async(Dispatchers.IO) {
                    extractKoreanTag(title, content)
                }
                //영문 태그 추출
                val enTagAsync = async(Dispatchers.IO) {
                    extractEnglishTag(title, content)
                }

                koTag = koTagAsync.await()
                enTag = enTagAsync.await()
            }

            //국문 영문 빈도수 TOP5 추출 후 반환
            return getTagsByTagCount(koTag, enTag)
        }

        /**
         * 한국어 형태소 분석기를 통한 한글 키워드 추출
         * - 제목과 컨텐츠를 합친 내용을 기준으로 키워드 추출
         * - 추출 시 빈도수가 높은 순에서 개수는 1-5개까지만 추출
         * - 1글자는 제외
         * - 공백 제외
         * @param title String
         * @param content String
         * @return List<String>
         */
        fun extractKoreanTag(title: String, content: String): List<Pair<String, Int>> {

            val komoran = Komoran(DEFAULT_MODEL.FULL)
            val resultMap = mutableMapOf<String, Int>()

            // 추출 문장
            val target = title + content

            // TODO: 특수문자는 추후 정립 후 반영 -> 키워드로 사용되는 경우 정리 필요.
            //특문 & 공백 제거
            val replaceTarget = target.replace(Regex("[^가-힣a-zA-Z0-9!@#$%^&*(]"), " ").trim()

            //명사 단어만 추출
            val analyzeResults = komoran.analyze(replaceTarget).tokenList.filter {
                it.pos == "NNG" || it.pos == "NNP" || it.pos == "NNB" || it.pos == "NP" || it.pos == "NR"
            }.map { it.morph }

            //중복 제거
            val resultSet = analyzeResults.filter { it.length > 1 }.toSet()

            //빈도수
            resultSet.forEach {
                val frequency = Collections.frequency(analyzeResults, it)
                resultMap[it] = frequency
            }

            //정렬 후 갯수 제한
            var result = resultMap.toList().sortedByDescending { it.second }
            if(result.size > TAG_COUNT) {
                result = result.subList(0, TAG_COUNT)
            }

            println(result)

            //키만 리스트로 반환
            return result
        }


        /**
         * 영어 형태소 분석기를 통한 영어 키워드 추출
         * - Jcseg 라이브러리 그대로 이용 시 키워드 추출은 가능하지만 빈도수의 관련된 정보를 알 수가 없어 커스텀 구현
         * - 제목과 컨텐츠를 합친 내용을 기준으로 키워드 추출
         * - 추출 시 빈도수가 높은 순에서 개수는 1-5개까지만 추출
         * - 1글자는 제외
         * - 공백 제외
         * @param title String
         * @param content String
         * @return List<Pair<String, Int>>
         */
        fun extractEnglishTag(title: String, content: String): List<Pair<String, Int>> {

            // 추출 문장
            val target = title + content

            // Jcseg 설정
            val config = SegmenterConfig(true)
            config.setClearStopwords(true)
            config.setAppendCJKSyn(false)
            config.setKeepUnregWords(false)
            val dic = DictionaryFactory.createSingletonDictionary(config)
            val seg = ISegment.COMPLEX.factory.create(config, dic)

            val words: MutableList<String> = ArrayList()
            val reader = StringReader(target)

            //단어 추출
            var w: IWord? = null
            seg.reset(reader)
            while (seg.next().also { w = it } != null) {
                if (w?.let { filter(it) } == false) continue
                val word = w!!.value
                if (word.length < 3) continue
                words.add(word)
            }

            println(words)

            //중복 제거
            val resultSet = words.toSet()
            //결과
            val resultMap = mutableMapOf<String, Int>()

            //빈도수 설정
            resultSet.forEach {
                val frequency = Collections.frequency(words, it)
                resultMap[it] = frequency
            }

            println(resultMap.toString())

            //정렬 후 갯수 제한
            var result = resultMap.toList().sortedByDescending { it.second }
            if(result.size > TAG_COUNT) {
                result = result.subList(0, TAG_COUNT)
            }

            return result
        }

        /**
         * 국문과 영문 키워드에서 최종 전달한 빈도수 TagCount 값만큼 키워드 추출
         * @param koTag List<Pair<String, Int>>
         * @param enTag List<Pair<String, Int>>
         * @return List<String>
         */
        fun getTagsByTagCount(koTag: List<Pair<String, Int>>, enTag: List<Pair<String, Int>>): List<String> {

            val resultTag: MutableList<Pair<String, Int>> = (koTag + enTag).toMutableList()

            return if(resultTag.size > TAG_COUNT) {
                resultTag.sortedByDescending { it.second }.subList(0, TAG_COUNT).map { it.first }
            } else {
                resultTag.sortedByDescending { it.second }.map { it.first }
            }
        }

        /**
         * Jcseg 에서 사용하는 영문 필터 함수
         * @param word
         * @return
         */
        private fun filter(word: IWord): Boolean {

            if (word.value.length < 2) {
                return false
            }
            when (word.type) {
                IWord.T_LETTER_NUMBER, IWord.T_OTHER_NUMBER, IWord.T_CJK_PINYIN, IWord.T_PUNCTUATION, IWord.T_UNRECOGNIZE_WORD -> {
                    return false
                }
            }

            //part of speech check
            val poss = word.partSpeech ?: return true
            val pos = poss[0][0]
            when (pos) {
                'e' -> {
                    return if (poss[0] == "en") true else false
                }
                'm' -> {
                    return if (poss[0] == "mix") true else false
                }
                'q', 'b', 'r', 'z', 'p', 'c', 'u', 'y', 'd', 'o', 'h', 'k', 'g', 'x', 'w' -> {
                    return false
                }
            }
            return true
        }

    }
}