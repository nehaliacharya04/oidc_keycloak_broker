package com.oidc.provider.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


/**
 * 
 * @author Nehali Acharya
 * 
 * A default login page is displayed which takes in 
 * basic user credentials like username/password.
 * 
 * Upon successful authentication, the Spring Authorization
 * Server issues an access token and sends it to Keycloak.
 * 
 * A JSESSIONID is generated at login and captured in a 
 * custom filter to maintain a track of active user sessions.
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	@Autowired
	JSessionCookieFilter cookieFilter;

	@Autowired
	IdpLogoutHandler idpLogoutHandler;

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.formLogin(form -> form.permitAll()).addFilterAfter(cookieFilter, ChannelProcessingFilter.class)
				.logout().addLogoutHandler(idpLogoutHandler);

		return http.build();
	}

	@Bean
	public UserDetailsService users() {
		var user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
}
