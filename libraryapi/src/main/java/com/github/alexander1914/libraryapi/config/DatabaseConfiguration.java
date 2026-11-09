package com.github.alexander1914.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource databaseConfiguration(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    //@Bean
    public DataSource hikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driver);

        /// Settings to my datasource
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setPoolName("library-db-pool");
        hikariConfig.setMaxLifetime(600000);// 600 mil ms (10 minutes)
        hikariConfig.setConnectionTimeout(100000); // to define a timeout your datasource
        hikariConfig.setConnectionTestQuery("select 1");

        return new HikariDataSource(hikariConfig);
    }
}
