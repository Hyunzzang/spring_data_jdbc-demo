package com.example.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@EnableJdbcRepositories(basePackages = "com.example.jdbc.testdb1.repository",
        jdbcOperationsRef = "testDB1NamedParameterJdbcOperations",
        dataAccessStrategyRef = "testDB1AccessStrategy",
        transactionManagerRef = "testDB1TransactionManager")
public class TestDB1JdbcConfig {

    @Bean("testDB1DataSource")
    @ConfigurationProperties("spring.testdb1.datasource")
    public DataSource testDB1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("testDB1NamedParameterJdbcOperations")
    public NamedParameterJdbcOperations testDB1NamedParameterJdbcOperations(@Qualifier("testDB1DataSource") DataSource testDB1DataSource) {
        return new NamedParameterJdbcTemplate(testDB1DataSource);
    }

    @Bean
    public DataAccessStrategy testDB1AccessStrategy(NamedParameterJdbcOperations testDB1NamedParameterJdbcOperations,
                                                    JdbcConverter jdbcConverter,
                                                    JdbcMappingContext context) {
        return new DefaultDataAccessStrategy(
                new SqlGeneratorSource(context, jdbcConverter,
                        DialectResolver.getDialect(testDB1NamedParameterJdbcOperations.getJdbcOperations())),
                context, jdbcConverter, testDB1NamedParameterJdbcOperations);
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
//    @Primary
//    public JdbcConverter jdbcConverter(
//            JdbcMappingContext mappingContext,
//            NamedParameterJdbcOperations testDB1NamedParameterJdbcOperations,
//            @Lazy RelationResolver relationResolver,
//            JdbcCustomConversions conversions
//    ) {
//        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(
//                testDB1NamedParameterJdbcOperations.getJdbcOperations());
//        Dialect dialect = DialectResolver.getDialect(testDB1NamedParameterJdbcOperations.getJdbcOperations());
//        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
//                dialect.getIdentifierProcessing());
//    }

//    @Bean
//    public JdbcCustomConversions jdbcCustomConversions() {
//        return new JdbcCustomConversions();
//    }
//
//    @Bean
//    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
//                                                 JdbcCustomConversions customConversions) {
//        JdbcMappingContext mappingContext = new JdbcMappingContext(
//                namingStrategy.orElse(NamingStrategy.INSTANCE));
//        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
//        return mappingContext;
//    }

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
