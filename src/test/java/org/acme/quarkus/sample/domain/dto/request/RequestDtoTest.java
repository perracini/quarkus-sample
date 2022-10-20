package org.acme.quarkus.sample.domain.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RequestDtoTest {
	
	@Test
	@DisplayName("testPostCreateEmployeeRequest")
	void testPostCreateEmployeeRequest() {
		CreateEmployeeRequest request = new CreateEmployeeRequest();
		request.setName(RandomStringUtils.randomAlphabetic(10));
		assertThat(request.getName()).isNotNull();
	}
	
	

}
