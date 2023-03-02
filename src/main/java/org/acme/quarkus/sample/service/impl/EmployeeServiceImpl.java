package org.acme.quarkus.sample.service.impl;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.quarkus.exception.ServiceException;

import org.acme.quarkus.sample.data.usecase.implementation.EmployeeUseCase;

import org.acme.quarkus.sample.domain.dto.request.EmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;
import org.acme.quarkus.sample.domain.dto.response.EmployeeResponse;

import org.acme.quarkus.sample.infra.dataprovider.EmployeeMapper;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.acme.quarkus.sample.service.IEmployeeService;
import org.acme.quarkus.sample.utils.Utils;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@RegisterForReflection
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Inject
	EmployeeUseCase employeeUseCase;
	
	@Inject
	EmployeeMapper mapper;

	@Override
	@Transactional
	public void save(@Valid EmployeeRequest createEmployeeRequest) {
		Employee emp = mapper.toEntity(createEmployeeRequest);
		employeeUseCase.save(emp);
	}

	@Override
	public EmployeeResponse findById(Integer employeeId) {
		Map<String, Object> idMap = Utils.createMapId("employeeId", employeeId);
        Employee entity = employeeUseCase.getById(idMap).orElseThrow(() -> new ServiceException("Employee not found"));
        
        return mapper.toResponse(entity);
	}

	@Override
	public Pagination<EmployeeResponse> getAll(PageRequest page) {
		Pagination<Employee> employeesList = employeeUseCase.getAll(page);
		Pagination<EmployeeResponse>  responseList= this.mapper.toResponsePage(employeesList);
		
		return responseList;
	}

	@Override
	@Transactional
	public void update(@Valid EmployeeRequest updateEmployeeRequest) {
		Map<String, Object> idMap = Utils.createMapId("employeeId", updateEmployeeRequest.getEmployeeId());
	    Employee entity = employeeUseCase.getById(idMap).orElseThrow(() -> new ServiceException("Employee not found"));   
		mapper.updateEmployeeFromDto(updateEmployeeRequest, entity);
		employeeUseCase.save(entity);
	}

	@Override
	public void delete(Integer employeeId) {
		// TODO Auto-generated method stub
	}

	@Override
	public Pagination<EmployeeResponse> getAllByStartingLetter(PageRequest page) {
		Pagination<Employee> employeesList = employeeUseCase.getAllByStartingLetter(page);
		Pagination<EmployeeResponse>  responseList= this.mapper.toResponsePage(employeesList);
		
		return responseList;
	}

}
