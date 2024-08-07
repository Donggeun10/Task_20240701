package com.example.demo.configuration;

import com.example.demo.util.AESUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommConfig {

    @Bean
    public CsvMapper csvMapper() {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        return csvMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDS = new HikariDataSource();

        hikariDS.setDriverClassName(dataSourceProperties.getDriverClassName());
        hikariDS.setJdbcUrl(dataSourceProperties.getUrl());
        hikariDS.setUsername(dataSourceProperties.getUsername());
        hikariDS.setPassword(AESUtil.decrypt(dataSourceProperties.getPassword()));

        return hikariDS;
    }
}
