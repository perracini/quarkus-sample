package org.acme.quarkus.sample.data.db.repository;

import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;

import org.acme.quarkus.sample.utils.PanacheQueryExtends;
import org.acme.quarkus.sample.utils.Utils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.impl.GenerateBridge;

public interface IRepository<E,I> extends PanacheRepositoryBase<E, I>{
	
	@GenerateBridge
	default Pagination<E> findMyQuery(PageRequest pageRequest, String query){
		Sort sort = Utils.getSort(pageRequest.getOrderBy(), pageRequest.getSort());
		return  new PanacheQueryExtends<>(this.find(query, sort), pageRequest).pageable();
	}
	
	@GenerateBridge
	default Pagination<E> find(PageRequest pageRequest){
		Sort sort = Utils.getSort(pageRequest.getOrderBy(), pageRequest.getSort());
		return new PanacheQueryExtends<>(this.findAll(sort), pageRequest).pageable();
	}
	
	
	/*@GenerateBridge
	default PanacheQueryExtends<E> findFilter(PageRequest pageRequest){
		Sort sort = Utils.getSort(pageRequest.getOrderBy(), pageRequest.getSort());
		String query = PanacheQueryConstructor.formatQuery(Utils.getClassParameters(this, pageRequest.getFilter());
		return  new PanacheQueryExtends<>(this.find(query, sort, new Parameters()), pageRequest);
		
	
	}*/
	

}
