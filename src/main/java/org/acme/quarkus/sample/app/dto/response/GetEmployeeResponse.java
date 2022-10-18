package org.acme.quarkus.sample.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetEmployeeResponse {
	
	private Integer employeeId;
	private String name;

}
