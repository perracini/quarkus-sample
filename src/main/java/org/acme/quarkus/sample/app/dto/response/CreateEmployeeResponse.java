package org.acme.quarkus.sample.app.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class CreateEmployeeResponse {
	
	private String code;
	private String message;

}
