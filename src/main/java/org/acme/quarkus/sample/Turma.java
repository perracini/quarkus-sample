package org.acme.quarkus.sample;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Turma {
	
	private Integer turmaId;

	@NotNull
	private String startDate;

	@NotNull
	private String endDate;
	
	private Integer instrutorId;

	private Integer alunoId;
	
}
