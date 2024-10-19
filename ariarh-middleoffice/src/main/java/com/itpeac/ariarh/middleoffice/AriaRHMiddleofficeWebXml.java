package com.itpeac.ariarh.middleoffice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.itpeac.ariarh.middleoffice.configuration.DefaultProfileUtil;

public class AriaRHMiddleofficeWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(com.itpeac.ariarh.middleoffice.AriaRHMiddleofficeBootstrapApplication.class);
    }
}