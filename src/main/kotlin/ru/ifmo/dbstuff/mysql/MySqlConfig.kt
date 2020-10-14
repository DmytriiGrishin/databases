package ru.ifmo.dbstuff.mysql

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
        basePackages = ["ru.ifmo.dbstuff.mysql"],
        entityManagerFactoryRef = "mysqlEntityManager",
        transactionManagerRef = "mysqlTransactionManager"
)
class MySqlConfig(val env: Environment) {
    @Bean
    @Primary
    fun mysqlEntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = mysqlDataSource()
        em.setPackagesToScan("ru.ifmo.dbstuff.mysql")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("hibernate.hbm2ddl.auto")
        properties["hibernate.dialect"] = env.getProperty("hibernate.dialect.mysql")
        em.setJpaPropertyMap(properties)
        return em
    }

    @Bean
    fun mysqlDataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(env.getProperty("spring.mysql-datasource.drive-class-name")!!)
        dataSource.url = env.getProperty("spring.mysql-datasource.url")
        dataSource.username = env.getProperty("spring.mysql-datasource.username")
        dataSource.password = env.getProperty("spring.mysql-datasource.password")
        return dataSource
    }

    @Primary
    @Bean
    fun mysqlTransactionManager(): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = mysqlEntityManager().getObject()
        return transactionManager
    }
}