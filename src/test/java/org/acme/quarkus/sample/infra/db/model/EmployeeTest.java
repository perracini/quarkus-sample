package org.acme.quarkus.sample.infra.db.model;

import org.acme.quarkus.sample.data.db.repository.IEmployeeRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class EmployeeTest {
	
	@InjectMock
	IEmployeeRepository repository;
	
	@InjectMock
	Session session;
	
	@Test
	void testEmployee() {
		
		Employee employee = new Employee();
		employee.setName("Name to check");
		employee.persist();
		Assertions.assertEquals("Name to check", employee.getName());
	}

}
