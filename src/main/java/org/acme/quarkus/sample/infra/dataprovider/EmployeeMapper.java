package org.acme.quarkus.sample.infra.dataprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import org.acme.quarkus.sample.domain.entity.EEmployee;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
	
	/*Employee eEmployeeToEmployee(final EEmployee eEmployee);
	
	@InheritInverseConfiguration
	EEmployee employeeToEEmployee(final Employee employee);*/
	
	default Employee eEmployeeToEmployee(final EEmployee eEmployee) {
		Employee employee = new Employee();
		employee.setName(eEmployee.getName());
		if(eEmployee.getEmployeeId() != null) {
			employee.setEmployeeId(eEmployee.getEmployeeId());
		}
		return employee;
	}
	
	default EEmployee employeeToEEmployee(final Employee employee) {
		EEmployee eEmployee = new EEmployee();
		eEmployee.setName(employee.getName());
		eEmployee.setEmployeeId(employee.getEmployeeId());
		return eEmployee;
	}
	
	default List<EEmployee> employeeToEEmployee(final List<Employee> employeeList){
		
		if(!employeeList.isEmpty()) {
			return employeeList.stream().map(emp -> employeeToEEmployee(emp)).collect(Collectors.toList());
		}
		return new ArrayList<>();
		
	}
	
	void updateEmployeeFromEEmployee(EEmployee eEmployee, @MappingTarget Employee employee);

}
