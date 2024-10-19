package com.itpeac.ariarh.middleoffice.configuration.apidoc;

import com.itpeac.ariarh.middleoffice.configuration.ApplicationConstants;
import com.itpeac.ariarh.middleoffice.configuration.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.DispatcherServlet;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ApiInfo.class, DispatcherServlet.class, Docket.class})
@Profile(ApplicationConstants.SPRING_PROFILE_SWAGGER)
@AutoConfigureAfter(ApplicationProperties.class)
@EnableSwagger2
public class SwaggerAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    /**
     * Swagger Springfox configuration.
     */
    @Bean
    public Docket swaggerSpringfoxDocket(ApplicationProperties applicationProperties) {
        LOGGER.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        ApiInfo apiInfo = new ApiInfo(
                applicationProperties.getSwagger().getTitle(),
                applicationProperties.getSwagger().getDescription(),
                applicationProperties.getSwagger().getVersion(),
                applicationProperties.getSwagger().getTermsOfServiceUrl(),
                new Contact(applicationProperties.getSwagger().getContactName(),
                        applicationProperties.getSwagger().getContactUrl(),
                        applicationProperties.getSwagger().getContactEmail()),
                applicationProperties.getSwagger().getLicense(),
                applicationProperties.getSwagger().getLicenseUrl(),
                new ArrayList<VendorExtension>());

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
                .securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(new ApiKey("JWT", "Authorization", "header")))
                .genericModelSubstitutes(ResponseEntity.class).forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .directModelSubstitute(org.joda.time.LocalDate.class, String.class)
                .directModelSubstitute(org.joda.time.LocalDateTime.class, Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDate.class, String.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class).select()
                .paths(regex(applicationProperties.getSwagger().getDefaultIncludePattern()))
                .build();
        watch.stop();
        LOGGER.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        SecurityReference sr = new SecurityReference("JWT", authorizationScopes);
        return Arrays.asList(sr);
    }

}
