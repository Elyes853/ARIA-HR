package com.itpeac.ariarh.middleoffice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {
    private Projet environnement = new Projet();
    private Http http = new Http();
    private Swagger swagger = new Swagger();
    private CorsConfiguration cors = new CorsConfiguration();
    private Email email = new Email();
    private static ApplicationProperties applicationProperties;
    private Token token = new Token();

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static ApplicationProperties getInstance() {
        return applicationProperties;
    }

    public ApplicationProperties() {
        applicationProperties = this;
    }

    public Projet getProjet() {
        return environnement;
    }

    public Http getHttp() {
        return http;
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public Email getEmail() {
        return email;
    }


    public CorsConfiguration getCors() {
        return cors;
    }


    public static class Projet {
        private String applicationName = ApplicationDefaults.Project.applicationName;
        private String applicationVersion = ApplicationDefaults.Project.applicationVersion;
        private String environnement = ApplicationDefaults.Project.environnement;

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public String getApplicationVersion() {
            return applicationVersion;
        }

        public void setApplicationVersion(String applicationVersion) {
            this.applicationVersion = applicationVersion;
        }

        public String getEnvironnement() {
            return environnement;
        }

        public void setEnvironnement(String environnement) {
            this.environnement = environnement;
        }

    }

    public static class Swagger {

        private String title = ApplicationDefaults.Swagger.title;
        private String version = ApplicationDefaults.Swagger.version;
        private String description = ApplicationDefaults.Swagger.description;
        private String termsOfServiceUrl = ApplicationDefaults.Swagger.termsOfServiceUrl;
        private String contactName = ApplicationDefaults.Swagger.contactName;
        private String licence = ApplicationDefaults.Swagger.license;
        private String licenceUrl = ApplicationDefaults.Swagger.licenseUrl;
        private String contactUrl = ApplicationDefaults.Swagger.contactUrl;
        private String contactEmail = ApplicationDefaults.Swagger.contactEmail;
        private String defaultIncludePattern = ApplicationDefaults.Swagger.defaultIncludePattern;

        public String getTitle() {
            return title;
        }

        public String getVersion() {
            return version;
        }

        public String getDescription() {
            return description;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public String getLicense() {
            return licence;
        }

        public String getLicenseUrl() {
            return licenceUrl;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public String getDefaultIncludePattern() {
            return defaultIncludePattern;
        }
    }

    public static class Http {
        private final Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = ApplicationDefaults.Http.Cache.timeToLiveInDays;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Email {
        private String from;
        private String sender;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

    public static class Token {
        private Long validity;

        public Long getValidity() {
            return validity;
        }

        public void setValidity(Long validity) {
            this.validity = validity;
        }

    }
}
