package org.acme.quarkus.sample.app.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.acme.quarkus.sample.app.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.response.CreateEmployeeResponse;
import org.acme.quarkus.sample.app.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.domain.usecase.IEmployeeUseCase;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@RegisterForReflection
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Inject
	IEmployeeUseCase iEmployeeUseCase;

	@Override
	public CreateEmployeeResponse save(@Valid CreateEmployeeRequest createEmployeeRequest) {
		return MapperResponse.eEmployeeToResponse(iEmployeeUseCase.save(createEmployeeRequest));
	}
	
	static class MapperResponse {
		
		public static  CreateEmployeeResponse eEmployeeToResponse(final Response response) {
			CreateEmployeeResponse createEmployeeResponse = new CreateEmployeeResponse();
			
			createEmployeeResponse.setCode("Employee-saved-001");
			createEmployeeResponse.setMessage("Employee included in database");
			
			return createEmployeeResponse;
		}
		
	}

	@Override
	public GetEmployeeResponse findById(Integer employeeId) {
		return iEmployeeUseCase.findById(employeeId);
	}

	@Override
	public List<GetEmployeeResponse> getAll() {
		return iEmployeeUseCase.getAll();
	}

}
