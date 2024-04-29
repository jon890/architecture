package com.bifos.recommend.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


@SpringBootTest
class RedisServiceTest(@Autowired private val redisService: RedisService) {

    private val logger = LoggerFactory.getLogger(RedisServiceTest::class.java)

    @Test
    @DisplayName("redis값 동시에 100번 변경 테스트")
    @Throws(InterruptedException::class)
    fun concurrencySetTest() {
        // given
        val COUNT = 100
        val balance = 1000L
        val subtract = 10L
        val testKey = UUID.randomUUID().toString()
        redisService.setLongValue(testKey, balance)

        // when
        val executorService = Executors.newFixedThreadPool(COUNT)
        val latch = CountDownLatch(COUNT)
        for (i in 0 until COUNT) {
            executorService.execute {
                logger.info("차감 시작 ===> {}", Thread.currentThread().name)
                redisService.minusLongValue(testKey, subtract)
                latch.countDown()
            }
        }
        latch.await()

        // then
        assertEquals(0L, redisService.getLongValue(testKey))
    }
}