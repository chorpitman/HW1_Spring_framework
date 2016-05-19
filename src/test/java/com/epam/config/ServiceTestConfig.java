package com.epam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * Created by Oleg_Chorpita on 5/19/2016.
 */
@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
public class ServiceTestConfig {
}
