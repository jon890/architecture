package com.bifos.password.config.database

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean

@ConfigurationProperties(prefix = "spring.datasource.multi")
class MultiDBProperties @ConstructorBinding constructor(
    private val user: List<DBProperty>
) {

    @Bean(name = ["multiUserDBProperties"])
    fun multiUserDBProperties(): List<DBProperty> {
        return user
    }
}