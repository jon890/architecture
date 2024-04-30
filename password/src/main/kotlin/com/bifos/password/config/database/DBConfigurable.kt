package com.bifos.password.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

interface DBConfigurable {

    fun dataSourceProperties(): DataSourceProperties

    fun dataSource(properties: DataSourceProperties): DataSource

    fun entityManager(dataSource: DataSource): LocalContainerEntityManagerFactoryBean

    fun transactionManager(entityManager: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager
}