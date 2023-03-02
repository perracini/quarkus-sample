package org.acme.quarkus.sample.presentation;


import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.quarkus.exception.ServiceException;
import org.acme.quarkus.sample.domain.dto.request.DepartmentRequest;
import org.acme.quarkus.sample.service.impl.DepartmentServiceImpl;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/department")
public class DepartmentEntrypoint {

	@Inject
	DepartmentServiceImpl departmentService;

	@POST
	@Tag(name = "add an Department")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response save(@NotNull @Valid DepartmentRequest departmentRequest) {
		departmentService.save(departmentRequest);
		return Response.status(201).build();
	}

	@GET
	@Tag(name = "get an Department")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{departmentId}")
	public Response findById(@PathParam("departmentId") Integer departmentId) {

		return Response.ok(departmentService.findById(departmentId)).status(Response.Status.OK).build();
	}

	/*@GET
	@Tag(name = "get all Departments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@BeanParam PageRequest page) {

		return Response.ok(departmentService.getAll(page)).status(Response.Status.OK).build();
	}
	
	@GET
	@Tag(name = "get all Departments Starting Letter")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/start")
	public Response getAllStart(@BeanParam PageRequest page) {

		return Response.ok(departmentService.getAllByStartingLetter(page)).status(Response.Status.OK).build();
	}*/

	@PUT
	@Tag(name = "update an Department")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@Path("/{departmentId}")
	public Response put(@Parameter(name = "departmentId", required = true) @PathParam("departmentId") Integer departmentId,
			@NotNull @Valid DepartmentRequest department) {
		if (!Objects.equals(departmentId, department.getDepartmentId())) {
			throw new ServiceException("Path variable departmentId does not match Department.departmentId");
		}
		departmentService.update(department);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/{departmentId}")
	public Response delete(@Parameter(name = "departmentId", required = true) @PathParam("departmentId") Integer departmentId) {

		departmentService.delete(departmentId);
		
		return Response.status(204).build();
	}
	
	@PUT
    @Path("/{departmentId}/employees/{employeeId}")
    @Transactional
    public Response addEmployeeToDepartment(
            @PathParam("departmentId") Integer departmentId,
            @PathParam("employeeId") Integer employeeId) {
        
            departmentService.addEmployeeToDepartment(departmentId, employeeId);
            return Response.status(Response.Status.NO_CONTENT).build();
       
    }
}
