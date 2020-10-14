package ru.ifmo.dbstuff.postgress

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.sql.DataSource


@Configuration
@PropertySource("classpath:persistence-multiple-db.properties")
@EnableJpaRepositories(
        basePackages = ["ru.ifmo.dbstuff.postgress"],
        entityManagerFactoryRef = "postgressEntityManager",
        transactionManagerRef = "postgressTransactionManager"
)
class PostgressConfig(val env: Environment) {
    @Bean
    @Primary
    fun postgressEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = postgressDataSource()
        em.setPackagesToScan("ru.ifmo.dbstuff.postgress.model")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("hibernate.hbm2ddl.auto")
        properties["hibernate.dialect"] = env.getProperty("hibernate.dialect.postgres")
        em.setJpaPropertyMap(properties)
        return em
    }

    @Bean
    fun postgressDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.postgress-datasource.drive-class-name")!!)
        dataSource.url = env.getProperty("spring.postgress-datasource.url")
        dataSource.username = env.getProperty("spring.postgress-datasource.username")
        dataSource.password = env.getProperty("spring.postgress-datasource.password")

        return dataSource
    }

    @Primary
    @Bean
    fun postgressTransactionManager(): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = postgressEntityManager().getObject()
        return transactionManager
    }
}