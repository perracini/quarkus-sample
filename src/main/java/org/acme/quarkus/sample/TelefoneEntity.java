package org.acme.quarkus.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity( name = "Telefone" )
@Table( name = "telefone" )
@Data
public class TelefoneEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "telefone_id")
	private Integer telefoneId;
	
	@Column(name= "ddd")
	private String ddd;
	
	@Column(name= "number")
	private String number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instrutor_id", nullable = false)
	private InstrutorEntity instrutor;

}
