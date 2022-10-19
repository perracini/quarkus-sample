package org.acme.quarkus.sample.infra.dataprovider;

import javax.inject.Inject;

import org.acme.quarkus.sample.domain.entity.EEmployee;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EmployeeDataProviderTest {

	@Inject
	EmployeeDataProvider employeeDataProvider;
	
	@Test
	void save() {
		EEmployee employee = new EEmployee();
		employee.setName(RandomStringUtils.randomAlphabetic(10));
		
		EEmployee saved = employeeDataProvider.save(employee);
		Assertions.assertNotNull(saved);
	}
	
}
