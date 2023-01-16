package com.resource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Nehali Acharya 
 * 
 * Protected REST endpoint accessible only to
 * authenticated users
 */

@RestController
@CrossOrigin("*")
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/hello")
	public ResponseEntity<String> getMessage() {
		LOGGER.info("Fetching message...");
		return new ResponseEntity<String>("Hi, How are you doing today?", HttpStatus.OK);
	}
}
