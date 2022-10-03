package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Titular {
	
	private Integer titularId;
	
	private String name;
	
	private List<Dependente> dependentes = new ArrayList<>();

}
