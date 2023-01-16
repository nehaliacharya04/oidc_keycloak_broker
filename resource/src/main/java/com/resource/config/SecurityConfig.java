package com.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 * @author Nehali Acharya
 * 
 * User must provide a valid JWT token issued
 * by Keycloak to access the protected resource
 */

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authz -> authz.antMatchers(HttpMethod.GET, "/hello/**").authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt());
		return http.build();
	}

}
