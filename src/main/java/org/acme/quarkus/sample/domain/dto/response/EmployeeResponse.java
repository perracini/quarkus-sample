package org.acme.quarkus.sample.domain.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class EmployeeResponse {
	
	private Integer employeeId;
	private String name;

}
