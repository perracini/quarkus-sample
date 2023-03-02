package org.acme.quarkus.sample.service;

import javax.validation.Valid;

import org.acme.quarkus.sample.domain.dto.request.DepartmentRequest;
import org.acme.quarkus.sample.domain.dto.response.DepartmentResponse;

public interface IDepartmentService {
	
	void save(final @Valid DepartmentRequest createDepartmentRequest);
	
	DepartmentResponse findById(Integer DepartmentId);
	
	void update(final @Valid DepartmentRequest updateDepartmentRequest);
	
	void delete(Integer DepartmentId);
	
	void addEmployeeToDepartment(Integer departmentId, Integer employeeId);
	

}
