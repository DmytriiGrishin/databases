package ru.ifmo.dbstuff.full

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
        basePackages = ["ru.ifmo.dbstuff.full"],
        entityManagerFactoryRef = "oraclefullEntityManager",
        transactionManagerRef = "oraclefullTransactionManager"
)
class OracleFullConfig(val env: Environment) {
    @Bean
    @Primary
    fun oraclefullEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = oraclefullDataSource()
        em.setPackagesToScan("ru.ifmo.dbstuff.full")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("hibernate.hbm2ddl.auto")
        properties["hibernate.dialect"] = env.getProperty("hibernate.dialect.full")
        em.setJpaPropertyMap(properties)
        return em
    }

    @Bean
    fun oraclefullDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.full-datasource.drive-class-name")!!)
        dataSource.url = env.getProperty("spring.full-datasource.url")
        dataSource.username = env.getProperty("spring.full-datasource.username")
        dataSource.password = env.getProperty("spring.full-datasource.password")
        return dataSource
    }

    @Primary
    @Bean
    fun oraclefullTransactionManager(): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = oraclefullEntityManager().getObject()
        return transactionManager
    }
}