package org.acme.quarkus.sample.infra.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.quarkus.sample.data.db.repository.IRepository;
import org.acme.quarkus.sample.infra.db.model.Department;

@ApplicationScoped
public class DepartmentRepository implements IRepository<Department, Integer>{

}
