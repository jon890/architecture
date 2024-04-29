package com.bifos.recommend.service

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Long>
) {
    private val logger = LoggerFactory.getLogger(RedisService::class.java)

    fun getLongValue(key: String): Long? {
        return redisTemplate.opsForValue().get(key)
    }

    fun setLongValue(key: String, value: Long) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun minusLongValue(key: String, value: Long) {
        val LOCK_KEY = "LOCK_${key}"
        lock(LOCK_KEY) {
            val currentValue = getLongValue(key)
            if (currentValue == null) {
                logger.error("해당 key에 대한 값이 존재하지 않습니다 key: {}", key)
                return@lock
            }
            val nextValue = currentValue - value
            logger.info("redis value updated with {} ==> {}", currentValue, nextValue)
            redisTemplate.opsForValue().set(key, nextValue)
        }
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }

    private fun lock(key: String, callback: () -> Unit) {
        while (true) {
            val locked = redisTemplate.opsForValue()
                .setIfAbsent(key, 1, Duration.ofMillis(1000))
            if (locked == true) {
                break
            }
        }

        callback()
        delete(key)
    }
}