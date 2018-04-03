package com.sample.scaffold.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.sample.scaffold.repository"}
        )
public class ApplicationConfigure {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean(name = "dataSource")
    @Primary
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dataSourceProperties.getMysqlUrl());
        datasource.setDriverClassName(dataSourceProperties.getMysqlDriverClassName());
        datasource.setUsername(dataSourceProperties.getMysqlUserName());
        datasource.setPassword(dataSourceProperties.getMysqlPassword());
        datasource.setName("dataSourceProperties-default");
        datasource.setMaxActive(100);
        datasource.setInitialSize(20);
        return datasource;
    }

    @Bean(name = "entityManager")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) throws SQLException {
        return entityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws SQLException {
        return builder
                .dataSource(dataSource())
                .properties(getVendorProperties(dataSource()))
                .packages("com.sample.scaffold.model") //设置实体类所在位置
                .persistenceUnit("scaffold-default")
                .build();
    }

    private Map<String, String> getVendorProperties(DruidDataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

}