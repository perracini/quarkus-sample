package org.acme.quarkus.sample;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TurmaFullDto {
	
	private Integer turmaId;

	@NotNull
	private String startDate;

	@NotNull
	private String endDate;
	
	private Instrutor instrutor;

	private Aluno aluno;

	private String status;
}
