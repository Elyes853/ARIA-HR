package com.itpeac.ariarh.middleoffice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.itpeac.ariarh.middleoffice.configuration.ApplicationConstants;
import com.itpeac.ariarh.middleoffice.configuration.ApplicationProperties;
import com.itpeac.ariarh.middleoffice.configuration.DefaultProfileUtil;

@SpringBootApplication(exclude = JdbcRepositoriesAutoConfiguration.class)
@EnableConfigurationProperties({ ApplicationProperties.class })
@EnableAsync
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AriaRHMiddleofficeBootstrapApplication implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(AriaRHMiddleofficeBootstrapApplication.class);

	private final Environment env;

	public AriaRHMiddleofficeBootstrapApplication(Environment env) {
		this.env = env;
	}

	/**
	 * Initializes ARIARHMiddleofficeApplication.
	 * <p>
	 * Spring profiles can be configured with a program argument
	 * --spring.profiles.active=your-active-profile
	 * <p>
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION)) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AriaRHMiddleofficeBootstrapApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (StringUtils.isBlank(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress,
				serverPort, contextPath, env.getActiveProfiles());
	}
}