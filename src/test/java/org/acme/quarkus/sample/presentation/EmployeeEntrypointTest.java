package org.acme.quarkus.sample.presentation;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ResourceBundle;

import org.acme.quarkus.exception.ErrorResponse;
import org.acme.quarkus.sample.domain.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.response.CreateEmployeeResponse;
import org.acme.quarkus.sample.domain.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.domain.dto.response.UpdateEmployeeResponse;
import org.acme.quarkus.sample.presentation.EmployeeEntrypoint;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

@QuarkusTest
@TestHTTPEndpoint(EmployeeEntrypoint.class)
@Slf4j
public class EmployeeEntrypointTest {
	
	@Test
	public void getAll() {
		given()
		.when()
		.get()
		.then()
		.statusCode(200);
	}

	@Test
	public void getByIdNotFound() {
		given().when().get("/{employeerId}", 987654321).then().statusCode(500);
	}

	@Test
	public void post() {
		CreateEmployeeRequest employee = createEmployee();
		CreateEmployeeResponse saved = given().contentType(ContentType.JSON).body(employee).post().then().statusCode(201).extract()
				.as(CreateEmployeeResponse.class);
		assertThat(saved.getCode()).hasToString("Employee-saved-001");
	}
	
	@Test
	public void getById() {
		/*CreateEmployeeRequest employee = createEmployee();
		CreateEmployeeResponse saved = given().contentType(ContentType.JSON).body(employee).post().then().statusCode(201).extract()
				.as(CreateEmployeeResponse.class);*/
	
		GetEmployeeResponse got = given()
				.when()
				.get("/{employeeId}",  1000000)
				.then()
				.statusCode(200).extract().as(GetEmployeeResponse.class);
		log.debug("id employee: {}", got.getEmployeeId());
		assertThat(got.getEmployeeId()).isNotNull();
	}

	@Test
	public void postFailNoName() {
		CreateEmployeeRequest employee = createEmployee();
		employee.setName(null);
		ErrorResponse errorResponse = given().contentType(ContentType.JSON).body(employee).post().then().statusCode(400)
				.extract().as(ErrorResponse.class);
		
		assertThat(errorResponse.getErrorId()).isNull();
		assertThat(errorResponse.getErrors()).isNotNull().hasSize(1)
				.contains(new ErrorResponse.ErrorMessage("save.createEmployeeRequest.name", getErrorMessage("Employee.name.required")));
	}

	@Test
	public void put() {
		GetEmployeeResponse got = given()
				.when()
				.get("/{employeeId}", 1000000)
				.then()
				.statusCode(200).extract().as(GetEmployeeResponse.class);
		log.debug("id employee 2: {}", got.getEmployeeId());
		UpdateEmployeeRequest employee = updateEmployee(got.getEmployeeId());
		UpdateEmployeeResponse response = given().contentType(ContentType.JSON).body(employee).put("/{employeeId}", employee.getEmployeeId()).then().statusCode(200).extract()
				.as(UpdateEmployeeResponse.class);
		assertThat(response.getCode()).hasToString("Employee-updated-002");
	}
	
	@Test
	public void delete() {
		CreateEmployeeRequest employee = createEmployee();
		given().contentType(ContentType.JSON).body(employee).post().then().statusCode(201);
		
		 given()
			.when()
			.delete("/{employeeId}", 1000001)
			.then()
			.statusCode(204);
	}

	private CreateEmployeeRequest createEmployee() {
		CreateEmployeeRequest employee = new CreateEmployeeRequest();
		employee.setName(RandomStringUtils.randomAlphabetic(10));
		return employee;
	}
	
	private UpdateEmployeeRequest updateEmployee(Integer id) {
		UpdateEmployeeRequest employee = new UpdateEmployeeRequest();
		employee.setEmployeeId(id);
		employee.setName(RandomStringUtils.randomAlphabetic(10));
		return employee;
	}

	private String getErrorMessage(String key) {
		return ResourceBundle.getBundle("ValidationMessages").getString(key);
	}

}
