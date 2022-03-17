package com.example.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(basePackages = "com.example.jdbc.repository")
public class DemoJdbcConfig {

    @Bean("baseDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource baseDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource baseDataSource) {
        return new NamedParameterJdbcTemplate(baseDataSource);
    }

    @Bean
    public JdbcTemplate baseJdbcTemplate(DataSource baseDataSource) {
        return new JdbcTemplate(baseDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource baseDataSource) {
        return new DataSourceTransactionManager(baseDataSource);
    }
}
