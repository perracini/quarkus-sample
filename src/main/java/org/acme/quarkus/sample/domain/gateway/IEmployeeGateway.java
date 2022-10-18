package org.acme.quarkus.sample.domain.gateway;

import java.util.List;

import org.acme.quarkus.sample.domain.entity.EEmployee;

public interface IEmployeeGateway {

	EEmployee save(final EEmployee eEmployee);
	
	EEmployee findById(Integer employeeId);
	
	List<EEmployee> getAll();
	
}
