package com.bifos.recommend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecommendApplication

fun main(args: Array<String>) {
    runApplication<RecommendApplication>(*args)
}
