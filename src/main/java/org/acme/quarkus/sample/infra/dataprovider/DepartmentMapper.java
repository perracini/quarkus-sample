package org.acme.quarkus.sample.infra.dataprovider;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.quarkus.sample.domain.dto.request.DepartmentRequest;
import org.acme.quarkus.sample.domain.dto.response.DepartmentResponse;
import org.acme.quarkus.sample.domain.dto.response.EmployeeResponse;
import org.acme.quarkus.sample.infra.db.model.Department;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {
	
  EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

  
  @Mapping(target = "departmentId", ignore = true)
  Department toEntity(DepartmentRequest departmentRequest);

  @Mapping(target = "employees", ignore = true)
  void updateDepartmentFromDto(DepartmentRequest departmentRequest, @MappingTarget Department department);
  
  /*default DepartmentResponse toResponse (Department department) {
	  if ( department == null ) {
	            return null;
	   }

	  DepartmentResponse departmentResponse = new DepartmentResponse();
      departmentResponse.setDepartmentId( department.getDepartmentId() );
      departmentResponse.setName( department.getName() );
      
      if(department.getEmployees() != null) {
	      for(Employee emp : department.getEmployees()) {
	
	          EmployeeResponse employeeResponse = new EmployeeResponse();
	
	          employeeResponse.setEmployeeId( emp.getEmployeeId() );
	          employeeResponse.setName( emp.getName() );
	          departmentResponse.getEmployees().add(employeeResponse);
	      }
      }
      
     
      
      return departmentResponse;
      
  }*/
  
  @Mapping(source = "employees", target = "employees", qualifiedByName = "mapEmployeesToResponses")
  DepartmentResponse toResponse(Department department);
  
  @Named("mapEmployeesToResponses")
  @IterableMapping(qualifiedByName = "mapEmployeeToResponse")
  List<EmployeeResponse> mapEmployeesToResponses(List<Employee> employees);

  @Named("mapEmployeeToResponse")
  default EmployeeResponse mapEmployeeToResponse(Employee employee) {
      return employeeMapper.toResponse(employee);
  }
  
  /*default List<EmployeeResponse> toResponseList(List<Employee> employees) {
      return employees.stream()
                      .map(this::mapEmployeeToResponse)
                      .collect(Collectors.toList());
  }*/

}
