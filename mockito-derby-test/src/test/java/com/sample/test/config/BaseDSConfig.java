package com.sample.test.config;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据源基类
 *
 * @author wanchongyang
 * @date 2018/2/28
 */
public abstract class BaseDSConfig {
    @Autowired
    private PageHelperProperties pageHelperProperties;

    public abstract DataSource init();

    Properties loadPropertySource() {

        try {
            InputStream is = new ClassPathResource("jdbc-test.properties").getInputStream();
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("获取配置失败", e);
        }

    }

    DataSource create(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.setDriverClassName(properties.getProperty("driverClassName"));
        config.setConnectionTimeout(Long.valueOf(properties.getProperty("connectionTimeout")));
        config.setIdleTimeout(Long.valueOf(properties.getProperty("idleTimeout")));
        config.setMaxLifetime(Long.valueOf(properties.getProperty("maxLifetime")));
        config.setMaximumPoolSize(Integer.valueOf(properties.getProperty("maximumPoolSize")));
        config.setMinimumIdle(Integer.valueOf(properties.getProperty("minimumIdle")));
        config.setPoolName(properties.getProperty("poolName"));

        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("cachePrepStmts", Boolean.TRUE);
        config.addDataSourceProperty("useServerPrepStmts", Boolean.TRUE);
        return new HikariDataSource(config);
    }

    SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {

        VFS.addImplClass(SpringBootVFS.class);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("helperDialect", pageHelperProperties.getHelperDialect());
        properties.setProperty("reasonable", pageHelperProperties.getReasonable());
        properties.setProperty("supportMethodsArguments", pageHelperProperties.getSupportMethodsArguments());
        properties.setProperty("params", pageHelperProperties.getParams());
        properties.setProperty("autoRuntimeDialect", pageHelperProperties.getAutoRuntimeDialect());
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);

        //处理SQL语句拼接换行问题
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});

        sqlSessionFactoryBean.setTypeAliasesPackage("com.sample.test.domain");

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }
}
