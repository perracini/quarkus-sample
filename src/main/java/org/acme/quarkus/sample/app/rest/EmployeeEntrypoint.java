package org.acme.quarkus.sample.app.rest;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import org.acme.quarkus.sample.app.dto.request.CreateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.request.UpdateEmployeeRequest;
import org.acme.quarkus.sample.app.dto.response.CreateEmployeeResponse;
import org.acme.quarkus.sample.app.dto.response.UpdateEmployeeResponse;
import org.acme.quarkus.sample.app.service.IEmployeeService;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/employee")
public class EmployeeEntrypoint {

	@Inject
	IEmployeeService iEmployeeService;

	@POST
	@Tag(name = "add an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(@NotNull @Valid CreateEmployeeRequest createEmployeeRequest) {
		CreateEmployeeResponse createEmployeeResponse = iEmployeeService.save(createEmployeeRequest);
		return Response.status(201).entity(createEmployeeResponse).build();
	}

	@GET
	@Tag(name = "get an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{employeeId}")
	public Response findById(@PathParam("employeeId") Integer employeeId) {

		return Response.ok(iEmployeeService.findById(employeeId)).status(Response.Status.OK).build();
	}

	@GET
	@Tag(name = "get all Employees")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {

		return Response.ok(iEmployeeService.getAll()).status(Response.Status.OK).build();
	}

	@PUT
	@Tag(name = "update an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{employeeId}")
	public Response put(@Parameter(name = "employeeId", required = true) @PathParam("employeeId") Integer employeeId,
			@NotNull @Valid UpdateEmployeeRequest employee) {
		if (!Objects.equals(employeeId, employee.getEmployeeId())) {
			throw new ServiceException("Path variable employeeId does not match Employee.employeeId");
		}
		UpdateEmployeeResponse updateEmployeeResponse = iEmployeeService.update(employee);
		
		return Response.status(200).entity(updateEmployeeResponse).build();
	}
	
	@DELETE
	@Path("/{employeeId}")
	public Response delete(@Parameter(name = "employeeId", required = true) @PathParam("employeeId") Integer employeeId) {

		iEmployeeService.delete(employeeId);
		
		return Response.status(204).build();
	}
}
