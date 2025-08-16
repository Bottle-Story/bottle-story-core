//package com.kyj.fmk.core.tx.dataSource;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 DataSourceConfig
 */
////@Configuration
//@Profile("prd")
//public class DataSourceConfig {
//
//    @Value("${spring.datasource.url}")
//    private String writeDbUrl;
//
//    @Value("${spring.datasource.username}")
//    private String writeDbUsername;
//
//    @Value("${spring.datasource.password}")
//    private String writeDbPassword;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String writeDbDriverClassName;
//
//    @Value("${spring.read-datasource.url}")
//    private String readDbUrl;
//
//    @Value("${spring.read-datasource.username}")
//    private String readDbUsername;
//
//    @Value("${spring.read-datasource.password}")
//    private String readDbPassword;
//
//    @Value("${spring.read-datasource.driver-class-name}")
//    private String readDbDriverClassName;
//

/**
 * Write DB에 대한 DataSource 빈
 */
//    @Primary
//    @Bean(name = "writeDataSource")
//    public DataSource writeDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(writeDbUrl);
//        dataSource.setUsername(writeDbUsername);
//        dataSource.setPassword(writeDbPassword);
//        dataSource.setDriverClassName(writeDbDriverClassName);
//        return dataSource;
//    }
//
/**
 * ReadOnly DB에 대한 DataSource 빈
 */
//    @Bean(name = "readDataSource")
//    public DataSource readDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(readDbUrl);
//        dataSource.setUsername(readDbUsername);
//        dataSource.setPassword(readDbPassword);
//        dataSource.setDriverClassName(readDbDriverClassName);
//        return dataSource;
//    }
//
//
/**
 * 트랜잭션 시작시 데이터소스를 결정하기 위한 지연프록시
 */
//    @Bean
//    public DataSource dataSource() {
//        return new LazyConnectionDataSourceProxy(routingDataSource());
//    }
//
/**
 * 동적라우팅소스
 */
//    private AbstractRoutingDataSource routingDataSource() {
//        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("WRITE", writeDataSource());
//        targetDataSources.put("READ", readDataSource());
//        routingDataSource.setTargetDataSources(targetDataSources);
//        routingDataSource.setDefaultTargetDataSource(writeDataSource());
//        return routingDataSource;
//    }
//}