package com.oidc.provider.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Nehali Acharya
 * 
 * This class is a custom filter defined 
 * for capturing the JSESSIONID to manage
 * user sessions.
 */
@Component
public class JSessionCookieFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JSessionCookieFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String jSessionId = Arrays.asList(req.getCookies()).stream()
				.filter(cookie -> cookie.getName().equals(AuthServerConstants.JSESSIONID.getValue())).findAny().get()
				.getValue();
		LOGGER.info("JSESSIONID: {}", jSessionId);
		chain.doFilter(request, response);
	}
}