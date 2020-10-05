package com.companycob.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloteste")
public class HelloWorldController {

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public String getHelloWorld() {
		return "Hello World!";
	}
	
}
