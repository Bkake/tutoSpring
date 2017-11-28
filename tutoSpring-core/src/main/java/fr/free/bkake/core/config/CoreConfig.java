package fr.free.bkake.core.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "fr.free.bkake.core.domain")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class CoreConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @Profile("dev")
    public DataSource devDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Profile("dev")
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDev
            (EntityManagerFactoryBuilder builder) {
        return builder.dataSource(devDataSource())
                .properties(jpaPropertiesDev())
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Profile("prod")
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryProd
            (EntityManagerFactoryBuilder builder) {
        return builder.dataSource(prodDataSource())
                .properties(jpaPropertiesProd())
                .build();
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


    Map<String, String> jpaPropertiesProd() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.query.substitutions", "true 'Y', false 'N'");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.format_sql", "true");

        return props;
    }

    Map<String, String> jpaPropertiesDev() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.query.substitutions", "true 'Y', false 'N'");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop");

        return props;
    }
}
