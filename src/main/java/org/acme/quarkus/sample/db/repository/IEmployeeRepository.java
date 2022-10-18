package org.acme.quarkus.sample.db.repository;

import java.util.List;
import java.util.Optional;

import org.acme.quarkus.sample.infra.db.model.Employee;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface IEmployeeRepository extends PanacheRepositoryBase<Employee, Integer>{
	
	Employee save(final Employee employee);
	
	Optional<Employee> findByOptionalId(Integer employeeId);
	
	List<Employee> getAll();

}
