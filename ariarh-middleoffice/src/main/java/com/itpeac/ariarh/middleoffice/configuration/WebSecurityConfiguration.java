package com.itpeac.ariarh.middleoffice.configuration;

import com.itpeac.ariarh.middleoffice.configuration.security.JwtAuthenticationFilter;
import com.itpeac.ariarh.middleoffice.configuration.security.JwtAuthorizationFilter;
import com.itpeac.ariarh.middleoffice.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui.html", "/api/public/**","/webjars/**", "/v2/**", "/swagger-resources/**", "/api/files/**" , "/api/public/createusers" , "/upload" ,"/api/question", "/api/category", "/api/category/{label}", "/ask","/api/conversation/**", "/api/conversation/{id}", "/api/conversation/message","/api/offers/{id}","/api/offers", "/api/addCandidat","/api/offers/{id}", "/api/offers/{id}/candidates");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate" , "/listObjects" , "/upload", "/api/public/createusers", "/question", "/category", "/category/{label}", "/ask","/api/conversation", "/api/conversation/{id}", "/api/conversation/message","/api/offers/{id}","/api/offers",  "/api/addCandidat","/api/offers/{id}","/api/offers/{id}/candidates" ).permitAll()
                .and()
                .anonymous().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    /**
     * a method where we defined a custom implementation of UserDetailsService to load user-specific data in the security
     * framework. We have also used this method to set the encrypt method used by our application (BCryptPasswordEncoder).
     */

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * a method where we can allow/restrict our CORS support.
     * In our case we left it wide open by permitting requests from any source (/**).
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Add your Angular application's origin
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config); // Register CORS configuration for all URLs
        return source;
    }



}
    
	
 

   





