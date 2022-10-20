package org.acme.quarkus.sample.service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.acme.quarkus.sample.domain.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.response.CreateEmployeeResponse;
import org.acme.quarkus.sample.domain.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.domain.dto.response.UpdateEmployeeResponse;
import org.acme.quarkus.sample.domain.usecase.IEmployeeUseCase;
import org.acme.quarkus.sample.service.IEmployeeService;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@RegisterForReflection
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Inject
	IEmployeeUseCase iEmployeeUseCase;

	@Override
	public CreateEmployeeResponse save(@Valid CreateEmployeeRequest createEmployeeRequest) {
		return MapperResponse.eEmployeeToSaveResponse(iEmployeeUseCase.save(createEmployeeRequest));
	}
	
	static class MapperResponse {
		
		public static  CreateEmployeeResponse eEmployeeToSaveResponse(final Response response) {
			CreateEmployeeResponse createEmployeeResponse = new CreateEmployeeResponse();
			
			createEmployeeResponse.setCode("Employee-saved-001");
			createEmployeeResponse.setMessage("Employee included in database");
			
			return createEmployeeResponse;
		}
		
		public static  UpdateEmployeeResponse eEmployeeToUpdateResponse(final Response response) {
			UpdateEmployeeResponse updateEmployeeResponse = new UpdateEmployeeResponse();
			
			updateEmployeeResponse.setCode("Employee-updated-002");
			updateEmployeeResponse.setMessage("Employee updated in database");
			
			return updateEmployeeResponse;
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

	@Override
	public UpdateEmployeeResponse update(@Valid UpdateEmployeeRequest updateEmployeeRequest) {
		return MapperResponse.eEmployeeToUpdateResponse(iEmployeeUseCase.update(updateEmployeeRequest));
	}

	@Override
	public void delete(Integer employeeId) {
		iEmployeeUseCase.delete(employeeId);
		
	}
	
	

}
