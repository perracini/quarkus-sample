package org.acme.quarkus.sample;

import lombok.Data;

@Data
public class Dependente {
	
	private Integer dependenteId;
	
	private String name;
	
	private Integer titularId;

}
