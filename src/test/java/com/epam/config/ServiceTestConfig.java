package com.epam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
public class ServiceTestConfig {
}
