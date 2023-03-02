package org.acme.quarkus.sample.data.usecase.implementation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.quarkus.sample.domain.gateway.IGateway;
import org.acme.quarkus.sample.domain.usecase.IUseCase;
import org.acme.quarkus.sample.infra.db.model.Employee;

@ApplicationScoped
public class EmployeeUseCase implements IUseCase<Employee, Integer>{
	
	@Inject
	IGateway<Employee, Integer> gateway;

	@Override
	public IGateway<Employee, Integer> getGateway() {
		return gateway;
	}
	
	


}
