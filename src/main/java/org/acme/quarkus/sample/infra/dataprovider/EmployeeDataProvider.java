package org.acme.quarkus.sample.infra.dataprovider;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.quarkus.exception.ServiceException;
import org.acme.quarkus.sample.AlunoService;
import org.acme.quarkus.sample.data.db.repository.IEmployeeRepository;
import org.acme.quarkus.sample.domain.entity.EEmployee;
import org.acme.quarkus.sample.domain.gateway.IEmployeeGateway;
import org.acme.quarkus.sample.infra.db.model.Employee;

import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class EmployeeDataProvider implements IEmployeeGateway {

	@Inject
	IEmployeeRepository repository;

	@Inject
	EmployeeMapper mapper;

	@Override
	@Transactional
	public EEmployee save(EEmployee eEmployee) {
		Employee employee = mapper.eEmployeeToEmployee(eEmployee); // usecase to entity
		Employee employeeSave = repository.save(employee);
		log.debug("employee saved: {} with name {}", employeeSave.getEmployeeId(), employee.getName());
		return mapper.employeeToEEmployee(employeeSave);// entity to usecase
	}

	@Override
	public EEmployee findById(Integer employeeId) {

		Optional<Employee> employee = repository.findByOptionalId(employeeId);

		if (!employee.isPresent()) {
			throw new ServiceException("Employee not found");
		}

		return mapper.employeeToEEmployee(employee.get());
	}

	@Override
	public List<EEmployee> getAll() {
		return mapper.employeeToEEmployee(repository.getAll());
	}

	@Override
	@Transactional
	public EEmployee update(EEmployee eEmployee) {
		Optional<Employee> employee = repository.findByOptionalId(eEmployee.getEmployeeId());
		if (!employee.isPresent()) {
			throw new ServiceException("Employee not found");
		}
		mapper.updateEmployeeFromEEmployee(eEmployee, employee.get());
		Employee employeeUpdate = repository.update(employee.get());
		log.debug("employee updated: {} with name {}", employeeUpdate.getEmployeeId(), employeeUpdate.getName());
		return mapper.employeeToEEmployee(employeeUpdate);
	}

	@Override
	@Transactional
	public void delete(Integer employeeId) {
		Optional<Employee> employee = repository.findByOptionalId(employeeId);

		/*if (!employee.isPresent()) {
			throw new ServiceException("Employee not found");
		}*/
		if (employee.isPresent()) {
			repository.delete(employee.get());
		}
		return;
	}

}
