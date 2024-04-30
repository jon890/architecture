package com.bifos.password.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.util.regex.Pattern

class DataSourceRouting : AbstractRoutingDataSource() {

    val dataSourceKeys = mutableListOf<String>()
    private val numberRegex = Pattern.compile("[0-9]").toRegex()

    override fun determineCurrentLookupKey(): Any {
        val currentShardId = DatabaseContextHolder.getShardId()

        return if (currentShardId == null) {
            dataSourceKeys.first()
        } else {
            dataSourceKeys.first().replace(numberRegex, currentShardId)
        }
    }
}