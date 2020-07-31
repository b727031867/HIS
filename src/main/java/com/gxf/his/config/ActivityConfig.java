package com.gxf.his.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Driver;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/7/31 10:36
 * Activit 的配置文件
 */
@Configuration
@Slf4j
public class ActivityConfig extends AbstractProcessEngineAutoConfiguration {
    @Value("${activiti.datasource.url}")
    private String url;

    @Value("${activiti.datasource.username}")
    private String username;

    @Value("${activiti.datasource.password}")
    private String password;

    @Value("${activiti.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 配置Activiti Data Source
     *
     * @throws ClassNotFoundException 没有配置类导致的异常
     */
    @SuppressWarnings("unchecked")
    @Bean("activityDataSource")
    public DataSource activitiDataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * 配置Transaction Manager
     *
     * @throws ClassNotFoundException 没有配置类导致的异常
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws ClassNotFoundException {
        return new DataSourceTransactionManager(activitiDataSource());
    }

    /**
     * 设置引擎属性
     *
     * @throws ClassNotFoundException 没有配置类导致的异常
     */
    public ProcessEngineConfigurationImpl processEngineConfiguration() throws ClassNotFoundException {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(activitiDataSource());
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setTransactionManager(transactionManager());
        processEngineConfiguration.setAsyncExecutorActivate(true);
        processEngineConfiguration.setHistory("full");

        return processEngineConfiguration;
    }

    /**
     * 利用流程引擎設置實例產生流程引擎FactoryBean
     *
     * @throws ClassNotFoundException 没有配置类导致的异常
     */
    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean() throws ClassNotFoundException {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return processEngineFactoryBean;
    }

    @Bean
    @Primary
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException, ClassNotFoundException {

        return baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                transactionManager,
                springAsyncExecutor);
    }

}
