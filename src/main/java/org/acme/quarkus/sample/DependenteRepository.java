package org.acme.quarkus.sample;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class DependenteRepository implements PanacheRepositoryBase<DependenteEntity, Integer> {

	public List<DependenteEntity> findByTitular(TitularEntity t) {
		return find("titular", t).list();
	}

}
