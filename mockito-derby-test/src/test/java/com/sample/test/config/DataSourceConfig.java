package com.sample.test.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源配置类
 * @author wanchongyang
 * @date 2018/2/28
 */
@Configuration
@MapperScan(basePackages = "com.sample.test.mapper", sqlSessionTemplateRef = "hikariSqlSessionTemplate")
public class DataSourceConfig extends BaseDSConfig {

    @Bean(name = "hikariDataSource")
    @Primary
    public DataSource init() {
        Properties properties = loadPropertySource();
        DataSource dataSource = create(properties);
        return dataSource;
    }

    @Bean(name = "hikariSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource") DataSource dataSource) throws Exception {
        return getSqlSessionFactory(dataSource);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("hikariDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "hikariSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("hikariSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
