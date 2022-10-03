package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity( name = "Titular" )
@Table( name = "titular" )
@Data
public class TitularEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "titular_id")
	private Integer titularId;
	
    @Column(name= "name", nullable=false)
    private String name;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DependenteEntity> dependentes = new ArrayList<>();
    
    public void addDependente(DependenteEntity dependente) {
        this.dependentes.add(dependente);
        dependente.setTitular(this);
    }

}
