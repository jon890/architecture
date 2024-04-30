package com.bifos.password.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Duration

@Configuration
@EnableTransactionManagement // JPA, JDBC를 사용하고 있으므로 platformTransactionManager 사용 가능
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val clientConfig = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ofSeconds(2))
            .shutdownTimeout(Duration.ZERO)
            .build()

        return LettuceConnectionFactory(
            RedisStandaloneConfiguration(host, port),
            clientConfig
        )
    }

    @Bean
    fun stringToLongRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Long> {
        val redisTemplate = RedisTemplate<String, Long>()
            .apply {
                connectionFactory = redisConnectionFactory
                keySerializer = StringRedisSerializer.UTF_8
                setEnableTransactionSupport(true)
                afterPropertiesSet()
            }
        return redisTemplate
    }
}