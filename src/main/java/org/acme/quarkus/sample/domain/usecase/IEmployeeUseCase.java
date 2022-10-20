package org.acme.quarkus.sample.domain.usecase;

import java.util.List;

import javax.ws.rs.core.Response;

import org.acme.quarkus.sample.domain.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.response.GetEmployeeResponse;

public interface IEmployeeUseCase {
	Response save(CreateEmployeeRequest employee);
	
	GetEmployeeResponse findById(Integer employeeId);
	
	List<GetEmployeeResponse> getAll();
	
	Response update(UpdateEmployeeRequest employee);
	
	void delete(Integer employeeId);
}
