package com.bifos.password.config.database

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties

data class DBProperty(
    val uniqueResourceName: String,
    val url: String,
    val username: String,
    val password: String,
) {
    fun toDatasourceProperties(): DataSourceProperties {
        // 변수 섀도잉 떄문에 내부 변수 선언
        val _url = url
        val _username = username
        val _password = password

        return DataSourceProperties().apply {
            this.url = _url
            this.username = _username
            this.password = _password
        }
    }
}