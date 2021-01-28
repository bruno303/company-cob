package com.companycob.web.delegate.impl;

import com.companycob.web.swagger.model.ApiResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HelloDelegateImplTest.HelloDelegateImplContext.class)
public class HelloDelegateImplTest {

	@Configuration
	@Import(value = { HelloDelegateImpl.class })
	public static class HelloDelegateImplContext {

	}

	@Autowired
	private HelloDelegateImpl helloDelegate;

	@Test
	public void testHelloWorldMessage() {
		ResponseEntity<Object> response = helloDelegate.getHelloWorld();
		Object body = response.getBody();

		assertThat(body).isInstanceOf(ApiResponseDTO.class);
		var apiResponseDto = (ApiResponseDTO) body;

		assertThat(apiResponseDto).isNotNull();

		String messageExpected = String.format("Hello World from %s", helloDelegate.getClass().getName());

		assertThat(apiResponseDto.getMessage()).isEqualTo(messageExpected);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(apiResponseDto.getCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(apiResponseDto.getError()).isFalse();
	}
}
