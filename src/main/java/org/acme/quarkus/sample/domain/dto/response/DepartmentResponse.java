package org.acme.quarkus.sample.domain.dto.response;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class DepartmentResponse {
    private Integer departmentId;
    private String name;
    private List<EmployeeResponse> employees = new ArrayList<>();


}
