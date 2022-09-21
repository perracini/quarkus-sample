package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class TurmaRepository implements PanacheRepositoryBase<TurmaEntity, Integer> {
	
	
	
}
