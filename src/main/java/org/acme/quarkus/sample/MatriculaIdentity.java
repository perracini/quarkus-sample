package org.acme.quarkus.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class MatriculaIdentity implements Serializable	{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 334407306337133503L;
	@Column(name = "aluno_id")
	private Integer alunoId;
	@Column(name = "turma_id")
	private Integer turmaId;

	public MatriculaIdentity() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alunoId == null) ? 0 : alunoId.hashCode());
		result = prime * result + ((turmaId == null) ? 0 : turmaId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatriculaIdentity other = (MatriculaIdentity) obj;
		if (alunoId == null) {
			if (other.alunoId != null)
				return false;
		} else if (!alunoId.equals(other.alunoId))
			return false;
		if (turmaId == null) {
			if (other.turmaId != null)
				return false;
		} else if (!turmaId.equals(other.turmaId))
			return false;
		return true;
	}
	
}
