package org.acme.quarkus.sample.domain.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class DepartmentRequest {
	
	private Integer departmentId;
	
	@NotNull
	private String name;
	
    private List<EmployeeRequest> employees;

}
