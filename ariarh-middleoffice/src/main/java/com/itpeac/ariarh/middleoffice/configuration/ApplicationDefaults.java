package com.itpeac.ariarh.middleoffice.configuration;

public interface ApplicationDefaults {

    interface Project {
        String applicationName = "API AriaRH";
        String applicationVersion = "V1";
        String environnement = "development";
    }

    interface Http {
        interface Cache {
            int timeToLiveInDays = 1461; // 4 years (including leap day)
        }
    }

    interface Swagger {
        String title = "Application API AriaRH";
        String description = "API documentation";
        String version = "1.0.0";
        String termsOfServiceUrl = null;
        String contactName = null;
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String defaultIncludePattern = "/.*";
    }
}
