package org.acme.quarkus.sample;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity( name = "Matricula" )
@Table( name = "matricula" )
@Data
public class MatriculaEntity {

	@EmbeddedId
	private MatriculaIdentity matriculaIdentity;

	@Column(nullable = false, name = "status")
	private String status;

}
