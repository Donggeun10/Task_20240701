package com.example.demo.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@EnableConfigurationProperties
@ConfigurationProperties("spring.datasource")
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

}