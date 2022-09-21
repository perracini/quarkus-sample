package org.acme.quarkus.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity( name = "Instrutor" )
@Table( name = "instrutor" )
@Data
public class InstrutorEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "instrutor_id")
	private Integer instrutorId;
	
    @Column(name= "name", nullable=false)
    private String name;

}
