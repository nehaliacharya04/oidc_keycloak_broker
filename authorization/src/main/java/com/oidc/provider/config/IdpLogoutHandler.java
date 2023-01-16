package com.oidc.provider.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * 
 * @author Nehali Acharya
 * 
 * Once the logout process is completed by the Spring Authorization
 * Server, a redirect call has to be made in order to
 * terminate the user session in Keycloak.
 */

@Configuration
public class IdpLogoutHandler implements LogoutHandler {

	private static final String STATE = "state";
	private static final String STATE_QUERY_PARAM = "?state=";

	@Value("${keycloak.oidc.logout.redirect-url}")
	private String logoutRedirectUrl;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String state = request.getParameter(STATE);
		try {
			response.sendRedirect(logoutRedirectUrl + STATE_QUERY_PARAM + state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
