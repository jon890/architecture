package com.bifos.password.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.bifos.password.repository.user"],
    entityManagerFactoryRef = "userEntityManager",
    transactionManagerRef = "userTransactionManager"
)
class UserDBConfig(
    private val env: Environment,
    @Qualifier("multiUserDBProperties") private val userDBProperties: List<DBProperty>,
) : BaseDBConfig() {

    override fun getEnvironment(): Environment {
        return env
    }

    override fun getEntityPackagesToScan(): String {
        return "com.bifos.password.entity.user"
    }

    @Bean(name = ["userDataSource"])
    fun userDataSource(): DataSource {
        val router = DataSourceRouting()
        val routeMap = mutableMapOf<Any, Any>()

        userDBProperties.forEach {
            val dataSource = super.dataSource(it.toDatasourceProperties())
            router.dataSourceKeys.add(it.uniqueResourceName)
            routeMap[it.uniqueResourceName] = dataSource
        }

        return router.apply { setTargetDataSources(routeMap) }
    }

    @Bean(name = ["userEntityManager"])
    override fun entityManager(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        return super.entityManager(dataSource)
    }

    @Bean(name = ["userTransactionManager"])
    override fun transactionManager(entityManager: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return super.transactionManager(entityManager)
    }
}