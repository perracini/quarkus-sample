package org.acme.quarkus.sample.domain.dto.request;

import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import io.quarkus.panache.common.Sort;
import lombok.Data;

@Data
public class PageRequest {
	
	@QueryParam("offset")
	Integer offset;
	
	@QueryParam("limit")
	Integer limit;
	
	@QueryParam("sort")
	String sort;
	
	@QueryParam("orderBy")
	Sort.Direction orderBy;
	
	@Parameter(hidden=true)
	@QueryParam("filter")
	String filter;

}
