package org.acme.quarkus.sample.utils;

import java.util.Objects;

import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.runtime.PanacheQueryImpl;

public class PanacheQueryExtends<E> extends PanacheQueryImpl<E> {
	
	PanacheQuery<E> panacheQuery;
	PageRequest pageRequest;

	public PanacheQueryExtends(PanacheQuery<E> panacheQuery, PageRequest pageRequest) {
		super(null);
		this.panacheQuery = panacheQuery;
		this.pageRequest = pageRequest;
	}
	//criar um objeto maior page pra ser o atributo pagination
	
	public <T extends E> Pagination<T> pageable() {
		if(Objects.nonNull(pageRequest.getOffset())) {
			panacheQuery.page(pageRequest.getOffset()-1, pageRequest.getLimit());
			
			return Pagination.<T>builder()
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getLimit())
					.numPages(panacheQuery.pageCount())
					.list(panacheQuery.list())
					.build();
		}
		
		return Pagination.<T>builder()
				.list(panacheQuery.list())
				.build();
	}

}
