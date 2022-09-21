package org.acme.quarkus.sample;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity( name = "Turma" )
@Table( name = "turma" )
@Data
public class TurmaEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "turma_id")
	private Integer turmaId;

	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instrutor_id", nullable = true)
	private InstrutorEntity instrutor;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="aluno_id", nullable = true)
	private AlunoEntity monitor;
	
}

