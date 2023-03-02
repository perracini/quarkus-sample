package org.acme.quarkus.sample.data.usecase.implementation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.quarkus.sample.domain.gateway.IGateway;
import org.acme.quarkus.sample.domain.usecase.IUseCase;
import org.acme.quarkus.sample.infra.db.model.Department;

@ApplicationScoped
public class DepartmentUseCase implements IUseCase<Department, Integer>{
	
	@Inject
	IGateway<Department, Integer> gateway;

	@Override
	public IGateway<Department, Integer> getGateway() {
		return gateway;
	}
	
	


}
