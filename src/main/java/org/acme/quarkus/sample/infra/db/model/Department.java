package org.acme.quarkus.sample.infra.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@Entity(name = "Department")
@Table(name = "department")
@Getter 
@Setter
public class Department extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;
    
    @Column(name = "name")
    private String name;

   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Employee> employees;*/
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employee.setDepartment(null);
        this.employees.remove(employee);
    }
}
