package org.acme.quarkus.sample.service;

import javax.validation.Valid;

import org.acme.quarkus.sample.domain.dto.request.EmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;
import org.acme.quarkus.sample.domain.dto.response.EmployeeResponse;


public interface IEmployeeService {
	
	void save(final @Valid EmployeeRequest createEmployeeRequest);
	
	EmployeeResponse findById(Integer employeeId);
	
	Pagination<EmployeeResponse> getAll(PageRequest page);
	
	void update(final @Valid EmployeeRequest updateEmployeeRequest);
	
	void delete(Integer employeeId);
	
	Pagination<EmployeeResponse> getAllByStartingLetter(PageRequest page);

}
