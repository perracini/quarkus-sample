package org.acme.quarkus.sample.infra.db.model;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@Entity(name = "Employee")
@Table(name = "employee")
@Getter
@Setter
public class Employee extends PanacheEntityBase{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer employeeId;
	
    @Column(name= "name", nullable=false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    

}
