package fr.free.bkake.rest.config;

import fr.free.bkake.business.config.BusinessConfig;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;


@Configuration
@EnableAspectJAutoProxy
@SpringApplicationConfiguration
@ComponentScan(basePackages = {"fr.free.bkake.rest"})
@Import(value= {BusinessConfig.class})
public class RestConfig{

}
