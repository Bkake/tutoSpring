package fr.free.bkake.business.config;

import fr.free.bkake.core.config.CoreConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@SpringApplicationConfiguration
@ComponentScan(basePackages = {"fr.free.bkake"})
@EnableTransactionManagement
@Import(value= {CoreConfig.class})
public class BusinessConfig {

}
