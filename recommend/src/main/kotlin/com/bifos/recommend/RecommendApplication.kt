package com.bifos.recommend

import com.bifos.recommend.const.RedisKeyConst
import com.bifos.recommend.entity.User
import com.bifos.recommend.repository.UserRepository
import com.bifos.recommend.service.RedisService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
class RecommendApplication {
    @Bean
    fun init(userRepository: UserRepository, redisService: RedisService): CommandLineRunner {
        return CommandLineRunner { args ->
            redisService.setLongValue(RedisKeyConst.EVENT_RECOMMEND_BUDGET_KEY, 3_000_000_000)

            if (userRepository.count() == 0L) {
                for (i in 1..100) {
                    userRepository.save(
                        User(userId = "User$i", password = "1234")
                    )
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<RecommendApplication>(*args)
}
