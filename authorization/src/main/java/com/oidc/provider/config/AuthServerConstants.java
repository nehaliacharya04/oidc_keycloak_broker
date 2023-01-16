package com.oidc.provider.config;

/**
 * 
 * @author Nehali Acharya
 *
 * Class for defining constants.
 */
public enum AuthServerConstants {

	LOGIN("/login"), RSA("RSA"), NOOP("{noop}"), JSESSIONID("JSESSIONID");

	private String value;

	AuthServerConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
