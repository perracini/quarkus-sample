package org.acme.quarkus.sample.app.service;

import java.util.List;

import javax.validation.Valid;

import org.acme.quarkus.sample.app.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.response.CreateEmployeeResponse;
import org.acme.quarkus.sample.app.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.app.dto.response.UpdateEmployeeResponse;

public interface IEmployeeService {
	
	CreateEmployeeResponse save(final @Valid CreateEmployeeRequest createEmployeeRequest);
	
	GetEmployeeResponse findById(Integer employeeId);
	
	List<GetEmployeeResponse> getAll();
	
	UpdateEmployeeResponse update(final @Valid UpdateEmployeeRequest updateEmployeeRequest);
	
	void delete(Integer employeeId);

}
