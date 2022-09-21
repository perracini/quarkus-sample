package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class MatriculaRepository implements PanacheRepositoryBase<MatriculaEntity, MatriculaIdentity>{
	


}
