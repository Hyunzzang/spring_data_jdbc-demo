package com.example.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(basePackages = "com.example.jdbc.test.repository",
        jdbcOperationsRef = "baseNamedParameterJdbcOperations",
        transactionManagerRef = "baseTransactionManager")
public class DemoJdbcConfig {

    @Bean("baseDataSource")
    @ConfigurationProperties("spring.test.datasource")
    public DataSource baseDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("baseNamedParameterJdbcOperations")
    @Primary
    public NamedParameterJdbcOperations baseNamedParameterJdbcOperations(@Qualifier("baseDataSource") DataSource baseDataSource) {
        return new NamedParameterJdbcTemplate(baseDataSource);
    }

    @Bean("baseJdbcTemplate")
    public JdbcTemplate baseJdbcTemplate(DataSource baseDataSource) {
        return new JdbcTemplate(baseDataSource);
    }

    @Bean("baseTransactionManager")
    @Primary
    public PlatformTransactionManager baseTransactionManager(DataSource baseDataSource) {
        return new DataSourceTransactionManager(baseDataSource);
    }
}
