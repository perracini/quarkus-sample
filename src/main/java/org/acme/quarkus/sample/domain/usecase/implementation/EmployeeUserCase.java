package org.acme.quarkus.sample.domain.usecase.implementation;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.acme.quarkus.sample.app.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.app.service.EmployeeServiceUseCaseMapper;
import org.acme.quarkus.sample.domain.entity.EEmployee;
import org.acme.quarkus.sample.domain.gateway.IEmployeeGateway;
import org.acme.quarkus.sample.domain.usecase.IEmployeeUseCase;

@ApplicationScoped
public class EmployeeUserCase implements IEmployeeUseCase{
	
	@Inject
	IEmployeeGateway gateway;
	
	@Inject
	EmployeeServiceUseCaseMapper mapper;

	@Override
	public Response save(CreateEmployeeRequest employee) {
		gateway.save(mapper.createEmployeeRequestToEEmployee(employee));
		
		return Response.ok().build();
	}

	@Override
	public GetEmployeeResponse findById(Integer employeeId) {
		return mapper.eEmployeeToGetEmployeeResponse(gateway.findById(employeeId));
	}

	@Override
	public List<GetEmployeeResponse> getAll() {
		
		return mapper.eEmployeeToGetEmployeeResponse(gateway.getAll());
	}

	

}
