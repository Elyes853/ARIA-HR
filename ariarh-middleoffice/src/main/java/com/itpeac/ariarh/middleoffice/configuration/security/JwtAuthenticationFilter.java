package com.itpeac.ariarh.middleoffice.configuration.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpeac.ariarh.middleoffice.common.UserContext;
import com.itpeac.ariarh.middleoffice.common.UserDetailsWithId;
import com.itpeac.ariarh.middleoffice.configuration.ApplicationProperties;
import com.itpeac.ariarh.middleoffice.configuration.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        logger.info("failed authentication while attempting to access " + failed);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        switch (failed.getMessage()) {
            case "Bad credentials":
                response.addHeader("X-validation-error", "error.badCredentials");
                break;
            case "User is disabled":
                response.addHeader("X-validation-error", "error.userIsDisabled");
                break;
            case "User account is locked":
                response.addHeader("X-validation-error", "error.userIsLocked");

                break;
            default:
                response.addHeader("X-validation-error", "error.other");
                break;
        }


    }


    @SuppressWarnings("deprecation")
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        MDC.clear();
        MDC.put("action", "ARIARH-AUTHENTICATION");
        MDC.put("@ip", request.getLocalAddr());

        UserDetailsWithId user = (UserDetailsWithId) UserContext.getUserDetails();
        if (user != null) {
            LOGGER.info("{} has Authentificated ", user.getUsername());

            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
            Long validityToken = ApplicationProperties.getInstance().getToken().getValidity();
            String token = Jwts.builder()
                    .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                    .setIssuer(SecurityConstants.TOKEN_ISSUER)
                    .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + validityToken))
                    .claim("rol", roles)
                    .compact();
            response.setStatus(HttpStatus.OK.value(), "ok");

            response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);

            response.setContentType("application/json");
            // Get the printwriter object from response to write the required json object to the output stream
            PrintWriter out;
            try {
                out = response.getWriter();
                // writing user java object to response output stream as json
                new ObjectMapper().writeValue(out, user);
                out.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}