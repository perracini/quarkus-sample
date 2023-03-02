package org.acme.quarkus.sample.domain.dto.request;

import java.util.List;

import javax.ws.rs.QueryParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
	
	private Integer offset;
	private Integer limit;
	private Integer numPages;
	private List<T> list;
	

}
