package org.acme.quarkus.sample.service.impl;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.quarkus.exception.ServiceException;

import org.acme.quarkus.sample.data.usecase.implementation.DepartmentUseCase;
import org.acme.quarkus.sample.data.usecase.implementation.EmployeeUseCase;
import org.acme.quarkus.sample.domain.dto.request.DepartmentRequest;
import org.acme.quarkus.sample.domain.dto.response.DepartmentResponse;

import org.acme.quarkus.sample.infra.dataprovider.DepartmentMapper;
import org.acme.quarkus.sample.infra.db.model.Department;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.acme.quarkus.sample.service.IDepartmentService;
import org.acme.quarkus.sample.utils.Utils;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@RegisterForReflection
public class DepartmentServiceImpl implements IDepartmentService{
	
	@Inject
	DepartmentUseCase departmentUseCase;
	
	@Inject
	EmployeeUseCase employeeUseCase;
	
	@Inject
	DepartmentMapper mapper;

	@Override
	@Transactional
	public void save(@Valid DepartmentRequest createDepartmentRequest) {
		Department emp = mapper.toEntity(createDepartmentRequest);
		departmentUseCase.save(emp);
	}

	@Override
	public DepartmentResponse findById(Integer DepartmentId) {
		Map<String, Object> idMap = Utils.createMapId("departmentId", DepartmentId);
		//pode ser a embedded se for composta - usar builder na classe da chave @embeddable com os valores de cada aributo
        Department entity = departmentUseCase.getById(idMap).orElseThrow(() -> new ServiceException("Department not found"));
        
        return mapper.toResponse(entity);
	}

	/*@Override
	public Pagination<DepartmentResponse> getAll(PageRequest page) {
		Pagination<Department> DepartmentsList = DepartmentUseCase.getAll(page);
		Pagination<DepartmentResponse>  responseList= this.mapper.toResponsePage(DepartmentsList);
		
		return responseList;
	}*/

	@Override
	@Transactional
	public void update(@Valid DepartmentRequest updateDepartmentRequest) {
		Map<String, Object> idMap = Utils.createMapId("departmentId", updateDepartmentRequest.getDepartmentId());
	    Department entity = departmentUseCase.getById(idMap).orElseThrow(() -> new ServiceException("Department not found"));   
		mapper.updateDepartmentFromDto(updateDepartmentRequest, entity);
		departmentUseCase.save(entity);
	}

	@Override
	public void delete(Integer DepartmentId) {
		// TODO Auto-generated method stub
	}

	/*@Override
	public Pagination<DepartmentResponse> getAllByStartingLetter(PageRequest page) {
		Pagination<Department> DepartmentsList = DepartmentUseCase.getAllByStartingLetter(page);
		Pagination<DepartmentResponse>  responseList= this.mapper.toResponsePage(DepartmentsList);
		
		return responseList;
	}*/ 
	@Override
	@Transactional
    public void addEmployeeToDepartment(Integer departmentId, Integer employeeId) throws ServiceException {
		Map<String, Object> idMapDep = Utils.createMapId("departmentId", departmentId);
		Map<String, Object> idMapEmp = Utils.createMapId("employeeId", employeeId);
        Department department = departmentUseCase.getById(idMapDep).orElseThrow(() -> new ServiceException("Department not found"));
        Employee employee = employeeUseCase.getById(idMapEmp).orElseThrow(() -> new ServiceException("Employee not found"));

        department.addEmployee(employee);
        departmentUseCase.save(department);
    }

}