package com.sample.test.config;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wanchongyang on 2017/9/8.
 */
@Configuration
@MapperScan(basePackages = "com.sample.test.mapper", sqlSessionTemplateRef = "hikariSqlSessionTemplate")
public class DataSourceConfig {
    @Autowired
    private PageHelperProperties pageHelperProperties;
    @Autowired
    private HikariDataSourceProperties hikariDataSourceProperties;

    @Bean(name = "hikariDataSource")
    @Primary
    public DataSource hikariDataSource() {
        Properties dsProps = new Properties();
        dsProps.put("url", hikariDataSourceProperties.getUrl());
        dsProps.put("user", hikariDataSourceProperties.getUsername());
        dsProps.put("password", hikariDataSourceProperties.getPassword());
        dsProps.put("prepStmtCacheSize", 250);
        dsProps.put("prepStmtCacheSqlLimit", 2048);
        dsProps.put("cachePrepStmts", Boolean.TRUE);
        dsProps.put("useServerPrepStmts", Boolean.TRUE);

        Properties configProps = new Properties();
        if (!StringUtils.isBlank(hikariDataSourceProperties.getDataSourceClassName())) {
            configProps.put("dataSourceClassName", hikariDataSourceProperties.getDataSourceClassName());
        }

        if (!StringUtils.isBlank(hikariDataSourceProperties.getDriverClassName())) {
            configProps.put("driverClassName", hikariDataSourceProperties.getDriverClassName());
        }
        configProps.put("poolName", hikariDataSourceProperties.getPoolName());
        configProps.put("maximumPoolSize", hikariDataSourceProperties.getMaximumPoolSize());
        configProps.put("minimumIdle", hikariDataSourceProperties.getMinimumIdle());
        configProps.put("connectionTimeout", hikariDataSourceProperties.getConnectionTimeout());
        configProps.put("idleTimeout", hikariDataSourceProperties.getIdleTimeout());
        configProps.put("dataSourceProperties", dsProps);

        // A default max pool size of 10 seems reasonable for now, so no need to
        // configure for now.
        HikariConfig hc = new HikariConfig(configProps);
        HikariDataSource ds = new HikariDataSource(hc);
        return ds;
    }

    @Bean(name = "hikariSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

        Properties properties = new Properties();
        properties.setProperty("helperDialect", pageHelperProperties.getHelperDialect());
        properties.setProperty("reasonable", pageHelperProperties.getReasonable());
        properties.setProperty("supportMethodsArguments", pageHelperProperties.getSupportMethodsArguments());
        properties.setProperty("params", pageHelperProperties.getParams());
        properties.setProperty("autoRuntimeDialect", pageHelperProperties.getAutoRuntimeDialect());
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageInterceptor});

        // 在Spring Boot中，由于是嵌套Jar，导致Mybatis默认的VFS实现DefaultVFS无法扫描嵌套Jar中的类
        // jar包运行时无法识别mybatis别名bug解决方法
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.sample.test.domain");
        return bean.getObject();
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
