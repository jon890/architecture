package com.bifos.recommend

import com.bifos.recommend.entity.User
import com.bifos.recommend.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class RecommendApplication {

//    private val logger = LoggerFactory.getLogger(RecommendApplication::class.java)

    @Bean
    fun initUsers(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner { args ->
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
