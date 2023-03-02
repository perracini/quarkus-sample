package org.acme.quarkus.sample.domain.dto.request;

import javax.validation.constraints.NotNull;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class EmployeeRequest {
	
	private Integer employeeId;
	
	@NotNull
	private String name;

}
