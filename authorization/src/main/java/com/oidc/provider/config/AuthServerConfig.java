package com.oidc.provider.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

/**
 * 
 * @author Nehali Acharya 
 * 
 * Defines a custom filter chain and registers client
 * details that represent the Spring Authorization Server's OIDC
 * configuration in Keycloak.
 */
@Configuration
public class AuthServerConfig {

	@Value("${keycloak.oidc.clientid}")
	private String clientId;

	@Value("${keycloak.oidc.password}")
	private String password;

	@Value("${keycloak.oidc.redirect.uri}")
	private String redirectUri;

	@Value("${issuer-uri}")
	private String issuerUri;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		http.cors(Customizer.withDefaults())
				.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(
						new LoginUrlAuthenticationEntryPoint(AuthServerConstants.LOGIN.getValue())))
				.oauth2ResourceServer().jwt();

		return http.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		var registeredClient = RegisteredClient.withId(UUID.randomUUID().toString()).clientId(clientId)
				.clientSecret(AuthServerConstants.NOOP.getValue() + password)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).redirectUri(redirectUri)
				.scope(OidcScopes.OPENID).build();

		return new InMemoryRegisteredClientRepository(registeredClient);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
		var keyPair = generateRsaKey();
		var publicKey = (RSAPublicKey) keyPair.getPublic();
		var privateKey = (RSAPrivateKey) keyPair.getPrivate();
		var rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
		var jwkSet = new JWKSet(rsaKey);

		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().issuer(issuerUri).build();
	}

	private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(AuthServerConstants.RSA.getValue());
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}
}
