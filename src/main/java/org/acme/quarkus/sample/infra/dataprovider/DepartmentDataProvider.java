package org.acme.quarkus.sample.infra.dataprovider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.quarkus.sample.data.db.repository.IRepository;
import org.acme.quarkus.sample.domain.gateway.IGateway;
import org.acme.quarkus.sample.infra.db.model.Department;
import org.acme.quarkus.sample.infra.repository.DepartmentRepository;

@ApplicationScoped
public class DepartmentDataProvider implements IGateway<Department, Integer> {

    @Inject
    DepartmentRepository repository;

    @Override
    public IRepository<Department, Integer> getRepository() {
        return repository;
    }
}
