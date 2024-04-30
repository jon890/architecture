package com.bifos.password

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PasswordApplication

fun main(args: Array<String>) {
    runApplication<PasswordApplication>(*args)
}
