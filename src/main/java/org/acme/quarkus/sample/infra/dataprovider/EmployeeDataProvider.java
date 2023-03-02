package org.acme.quarkus.sample.infra.dataprovider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.quarkus.sample.data.db.repository.IRepository;
import org.acme.quarkus.sample.domain.gateway.IGateway;
import org.acme.quarkus.sample.infra.db.model.Employee;
import org.acme.quarkus.sample.infra.repository.EmployeeRepository;

@ApplicationScoped
public class EmployeeDataProvider implements IGateway<Employee, Integer> {
	
	@Inject
	EmployeeRepository repository;
	
	@Override
	public IRepository<Employee, Integer> getRepository() {
		return repository;
	}

}
