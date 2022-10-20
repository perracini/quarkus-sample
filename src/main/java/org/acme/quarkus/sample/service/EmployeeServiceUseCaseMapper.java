package org.acme.quarkus.sample.service;

import java.util.List;

import org.acme.quarkus.sample.domain.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.domain.dto.response.GetEmployeeResponse;
import org.acme.quarkus.sample.domain.entity.EEmployee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeServiceUseCaseMapper {
	
	CreateEmployeeRequest eEmployeeToCreateEmployeeRequest(final EEmployee eEmployee);
	
	@InheritInverseConfiguration
	EEmployee createEmployeeRequestToEEmployee(final CreateEmployeeRequest createEmployeeRequest);
	
	GetEmployeeResponse eEmployeeToGetEmployeeResponse(final EEmployee eEmployee);
	
	List<GetEmployeeResponse> eEmployeeToGetEmployeeResponse(final List<EEmployee> eEmployee);
	
	EEmployee updateEmployeeRequestToEEmployee(final UpdateEmployeeRequest updateEmployeeRequest);
	
	
	

}
