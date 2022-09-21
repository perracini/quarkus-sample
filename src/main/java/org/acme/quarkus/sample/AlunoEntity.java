package org.acme.quarkus.sample;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity( name = "Aluno" )
@Table( name = "aluno" )
@Data
public class AlunoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "aluno_id")
	private Integer alunoId;
	
    @Column(name= "name", nullable=false)
    private String name;

}
