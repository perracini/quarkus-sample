package org.acme.quarkus.sample.domain.dto.request;

import javax.validation.constraints.NotEmpty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@RegisterForReflection
public class CreateEmployeeRequest {
	
	@NotEmpty(message = "{Employee.name.required}")
	private String name;

}
