package com.sample.scaffold.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({MybatisDataSourceProperties.class})
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactory",
        basePackages = {"com.sample.scaffold.mapper"})
public class MybatisApplicationConfigure {

    @Autowired
    private MybatisDataSourceProperties mybatisDataSourceProperties;

    @Bean(name = "mybatisDataSource")
    public DruidDataSource mybatisDataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(mybatisDataSourceProperties.getMysqlUrl());
        datasource.setDriverClassName(mybatisDataSourceProperties.getMysqlDriverClassName());
        datasource.setUsername(mybatisDataSourceProperties.getMysqlUserName());
        datasource.setPassword(mybatisDataSourceProperties.getMysqlPassword());
        datasource.setName("mybatisDataSourceProperties-default");
        datasource.setMaxActive(100);
        datasource.setInitialSize(20);
        datasource.setDefaultReadOnly(true);
        return datasource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mybatisDataSource());
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:mapper/mybatis/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mybatisTransactionManager")
    public PlatformTransactionManager mybatisTransactionManager() throws SQLException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(mybatisDataSource());
        transactionManager.setDefaultTimeout(5);
        return transactionManager;
    }

    @Bean(name = "mybatisTransactionInterceptor")
    public TransactionInterceptor mybatisTransactionInterceptor() throws SQLException {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(mybatisTransactionManager());
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED,-Exception");
        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        return transactionInterceptor;
    }


}