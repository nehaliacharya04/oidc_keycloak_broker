package com.oidc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OidcAuthorizationServer {

	public static void main(String[] args) {
		SpringApplication.run(OidcAuthorizationServer.class, args);
	}

}
