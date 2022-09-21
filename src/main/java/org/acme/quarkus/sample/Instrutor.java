package org.acme.quarkus.sample;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Instrutor {
	
	private Integer instrutorId;
	
	@NotEmpty(message = "{Instrutor.name.required}")
	private String name;


}

