package com.example.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
@EnableJdbcRepositories(basePackages = "com.example.jdbc.repository",
        transactionManagerRef = "baseTransactionManager")
public class DemoJdbcConfig extends AbstractJdbcConfiguration {

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
    public PlatformTransactionManager baseTransactionManager(DataSource baseDataSource) {
        return new DataSourceTransactionManager(baseDataSource);
    }

//    @Bean
//    public DataSourceInitializer h2DataSourceInitializer(
//            @Qualifier("baseDataSource") final DataSource dataSource) {
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//        dataSourceInitializer.setDataSource(dataSource);
//        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
//        return dataSourceInitializer;
//    }
}
