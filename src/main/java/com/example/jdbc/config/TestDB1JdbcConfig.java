package com.example.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(basePackages = "com.example.jdbc.testdb1.repository",
        jdbcOperationsRef = "testDB1NamedParameterJdbcOperations",
        transactionManagerRef = "testDB1TransactionManager")
public class TestDB1JdbcConfig extends AbstractJdbcConfiguration {

    @Bean("testDB1DataSource")
    @ConfigurationProperties("spring.testdb1.datasource")
    public DataSource testDB1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("testDB1NamedParameterJdbcOperations")
    public NamedParameterJdbcOperations testDB1NamedParameterJdbcOperations(@Qualifier("testDB1DataSource") DataSource testDB1DataSource) {
        return new NamedParameterJdbcTemplate(testDB1DataSource);
    }

    @Bean("testDB1JdbcTemplate")
    public JdbcTemplate testDB1JdbcTemplate(DataSource testDB1DataSource) {
        return new JdbcTemplate(testDB1DataSource);
    }

    @Bean("testDB1TransactionManager")
    public PlatformTransactionManager testDB1TransactionManager(DataSource testDB1DataSource) {
        return new DataSourceTransactionManager(testDB1DataSource);
    }

//    @Bean
//    public DataSourceInitializer testDB1DataSourceInitializer(
//            @Qualifier("testDB1DataSource") final DataSource dataSource) {
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//        dataSourceInitializer.setDataSource(dataSource);
//        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
//        return dataSourceInitializer;
//    }
}
