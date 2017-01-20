package fr.free.bkake.core.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"fr.free.bkake.core.repository"})
@EntityScan(basePackages = {"fr.free.bkake.core..domain"})
public class CoreConfig {
}
