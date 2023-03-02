package org.acme.quarkus.sample.infra.dataprovider;

import org.acme.quarkus.sample.domain.dto.request.EmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;
import org.acme.quarkus.sample.domain.dto.response.EmployeeResponse;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
	
     EmployeeResponse toResponse(Employee employee);
     
     Employee toEntity(EmployeeRequest request);
     
     void updateEmployeeFromDto(EmployeeRequest req, @MappingTarget Employee emp);
    
     Pagination<EmployeeResponse> toResponsePage(Pagination<Employee> response);
     
     //List<EmployeeResponse> toResponseList(List<Employee> response);

}
