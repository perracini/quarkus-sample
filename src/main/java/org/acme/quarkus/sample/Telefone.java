package org.acme.quarkus.sample;

import lombok.Data;

@Data
public class Telefone {
	
	private Integer telefoneId;
	private String ddd;
	
	@StringAsPhoneValid(message = "Valor inválido")
	private String number;

}
