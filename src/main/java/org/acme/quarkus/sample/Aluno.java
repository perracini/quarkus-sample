package org.acme.quarkus.sample;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Aluno {
	
	private Integer alunoId;
	
	@NotEmpty(message = "{Aluno.name.required}")
	private String name;

}
