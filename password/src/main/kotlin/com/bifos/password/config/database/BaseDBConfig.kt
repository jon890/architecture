package com.bifos.password.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.core.env.Environment
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

abstract class BaseDBConfig : DBConfigurable {

    abstract fun getEnvironment(): Environment

    abstract fun getEntityPackagesToScan(): String
    override fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    override fun dataSource(properties: DataSourceProperties): DataSource {
        return properties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    override fun entityManager(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setPackagesToScan(getEntityPackagesToScan())

            val rootPath = "spring.jpa.properties"
            val properties = listOf(
                "hibernate.hbm2ddl.auto",
                "hibernate.dialect",
                "hibernate.show_sql",
                "hibernate.format_sql"
            ).associateWith { getEnvironment().getProperty("${rootPath}.${it}") }
            setJpaPropertyMap(properties)
            afterPropertiesSet()
        }
    }

    override fun transactionManager(entityManager: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return JpaTransactionManager().apply {
            entityManagerFactory = entityManager.`object`
        }
    }
}