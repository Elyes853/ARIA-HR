package com.itpeac.ariarh.middleoffice.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.itpeac.ariarh.middleoffice.repository", transactionManagerRef = "ariarhJpaTransactionManager", entityManagerFactoryRef = "ariarhEntityManagerFactoryRef")
@EnableJpaAuditing
public class AriaRHDatabaseConfiguration {

    @Bean(name = "ariaRHDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasources.ariarh")
    public DataSource ariaRHDataSource() {
        return DataSourceBuilder.create().build();
    }


    @DependsOn("flyway")
    @Bean("ariarhEntityManagerFactoryRef")
    public LocalContainerEntityManagerFactoryBean ariarhEntityManagerFactoryRef() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ariaRHDataSource());
        em.setPackagesToScan("com.itpeac.ariarh.middleoffice.domain");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(hibernateProperties());
        em.afterPropertiesSet();

        return em;
    }

    private Map<String, Object> hibernateProperties() {

        Resource resource = new ClassPathResource("hibernate.properties");

        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
        } catch (IOException e) {
            return new HashMap<String, Object>();
        }
    }

    @Bean(name = "flywayConfiguration")
    @ConfigurationProperties(prefix = "spring.flyway.ariarh")
    public ClassicConfiguration flywayConfiguration() {
        return new ClassicConfiguration();
    }

    @Bean
    public Flyway flywayAriaRHDatabase(ClassicConfiguration flywayConfiguration) {
        Flyway flyway = Flyway.configure().configuration(flywayConfiguration).dataSource(this.ariaRHDataSource()).load();
        return flyway;
    }

    @Bean(name="flyway")
    public FlywayMigrationInitializer flyway(
            @Qualifier("flywayAriaRHDatabase") Flyway flywayAriaRHDatabase) {
        FlywayMigrationInitializer initializer = new FlywayMigrationInitializer(flywayAriaRHDatabase, null);
        return initializer;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean("ariarhJpaTransactionManager")
    public JpaTransactionManager ariarhJpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ariarhEntityManagerFactoryRef().getObject());

        return transactionManager;
    }

    @Bean
    public AuditorAware<String> securityBasedAuditorAware() {
        return new SpringSecurityAuditorAware();

    }

}

class SpringSecurityAuditorAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated).map(Authentication::getPrincipal).map(String.class::cast);
    }
}
