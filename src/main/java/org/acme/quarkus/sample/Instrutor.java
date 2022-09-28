package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Instrutor {
	
	private Integer instrutorId;
	
	@NotEmpty(message = "{Instrutor.name.required}")
	private String name;


	@Valid
	private List<Telefone> telefones = new ArrayList<>();
}

