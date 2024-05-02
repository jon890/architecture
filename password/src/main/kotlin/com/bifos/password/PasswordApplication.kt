package com.bifos.password

import com.bifos.password.entity.common.User
import com.bifos.password.repository.common.JdbcUserRepository
import com.bifos.password.repository.common.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
class PasswordApplication {

    private val logger = LoggerFactory.getLogger(PasswordApplication::class.java)

    @Bean
    fun init(
        jdbcUserRepository: JdbcUserRepository,
        jpaUserRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            val count = jpaUserRepository.count()

            if (count != 0L) {
                return@CommandLineRunner
            }

            // 1만 * 1만 => 1억
            for (i in 1..10000) {
                val users = mutableListOf<User>()
                val startTime = Date().time
                for (j in 1..10000) {
                    val userId = "User_${(i - 1) * 10000 + j}"
//                    val password = passwordEncoder.encode(userId)
                    val user = User(userId, userId)
                    users.add(user)
                }
                val objectCreatedTime = Date().time
                logger.info("초기 객체 생성 loop =>i:$i, time:${objectCreatedTime - startTime}ms")
                jdbcUserRepository.insertBatch(users)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<PasswordApplication>(*args)
}
