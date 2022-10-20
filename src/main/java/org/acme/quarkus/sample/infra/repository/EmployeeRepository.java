package org.acme.quarkus.sample.infra.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.acme.quarkus.sample.data.db.repository.IEmployeeRepository;
import org.acme.quarkus.sample.infra.db.model.Employee;

@ApplicationScoped
public class EmployeeRepository implements IEmployeeRepository{

	@Override
	public Employee save(Employee employee) {
		
		employee.persist();
		return employee;		
	}

	@Override
	public Optional<Employee> findByOptionalId(Integer employeeId) {
	
		return findByIdOptional(employeeId);
	}

	@Override
	public List<Employee> getAll() {
		
		return listAll();
	}

	@Override
	public Employee update(Employee employee) {
		
		employee.persist();
		return employee;
	}

}
