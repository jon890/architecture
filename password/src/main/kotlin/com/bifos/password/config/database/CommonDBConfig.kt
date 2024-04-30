package com.bifos.password.config.database

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.bifos.password.repository.common"],
    entityManagerFactoryRef = "commonEntityManager",
    transactionManagerRef = "commonTransactionManager"
)
class CommonDBConfig(
    private val env: Environment,
) : BaseDBConfig() {

    override fun getEnvironment(): Environment {
        return env
    }

    override fun getEntityPackagesToScan(): String {
        return "com.bifos.password.entity.common"
    }

    @Primary
    @Bean(name = ["commonDatasourceProperties"])
    @ConfigurationProperties(prefix = "spring.datasource.common")
    override fun dataSourceProperties(): DataSourceProperties {
        return super.dataSourceProperties()
    }

    @Primary
    @Bean(name = ["commonDatasource"])
    override fun dataSource(@Qualifier("commonDatasourceProperties") properties: DataSourceProperties): DataSource {
        return super.dataSource(properties)
    }

    @Primary
    @Bean(name = ["commonEntityManager"])
    override fun entityManager(@Qualifier("commonDatasource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        return super.entityManager(dataSource)
    }

    @Primary
    @Bean(name = ["commonTransactionManager"])
    override fun transactionManager(@Qualifier("commonEntityManager") entityManager: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return super.transactionManager(entityManager)
    }
}