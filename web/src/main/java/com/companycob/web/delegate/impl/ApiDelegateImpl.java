package com.companycob.web.delegate.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.companycob.web.swagger.api.ApiApiDelegate;
import com.companycob.web.swagger.model.ApiResponseDTO;

@Component
public class ApiDelegateImpl implements ApiApiDelegate {

	@Override
	public ResponseEntity<Object> getHelloWorld() {
		final ApiResponseDTO response = new ApiResponseDTO();
		response.setError(false);
		response.setMessage(String.format("Hello World from %s", this.getClass().getName()));
		response.setCode(HttpStatus.OK.value());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
