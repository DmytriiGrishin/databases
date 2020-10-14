package ru.ifmo.dbstuff.oracle

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
        basePackages = ["ru.ifmo.dbstuff.oracle"],
        entityManagerFactoryRef = "oracleEntityManager",
        transactionManagerRef = "oracleTransactionManager"
)
class OracleConfig(val env: Environment) {
    @Bean
    @Primary
    fun oracleEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = oracleDataSource()
        em.setPackagesToScan("ru.ifmo.dbstuff.oracle.model")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("hibernate.hbm2ddl.auto")
        properties["hibernate.dialect"] = env.getProperty("hibernate.dialect.oracle")
        em.setJpaPropertyMap(properties)
        return em
    }

    @Bean
    fun oracleDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.oracle-datasource.drive-class-name")!!)
        dataSource.url = env.getProperty("spring.oracle-datasource.url")
        dataSource.username = env.getProperty("spring.oracle-datasource.username")
        dataSource.password = env.getProperty("spring.oracle-datasource.password")
        return dataSource
    }

    @Primary
    @Bean
    fun oracleTransactionManager(): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = oracleEntityManager().getObject()
        return transactionManager
    }
}