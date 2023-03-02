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
import org.acme.quarkus.sample.domain.dto.request.EmployeeRequest;
import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.service.impl.EmployeeServiceImpl;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/employee")
public class EmployeeEntrypoint {

	@Inject
	EmployeeServiceImpl employeeService;
	
	//@Inject
	//EmployeeValidityServiceImpl employeeValidityService;

	@POST
	@Tag(name = "add an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response save(@NotNull @Valid EmployeeRequest employeeRequest) {
		employeeService.save(employeeRequest);
		return Response.status(201).build();
	}

	@GET
	@Tag(name = "get an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{employeeId}")
	public Response findById(@PathParam("employeeId") Integer employeeId) {

		return Response.ok(employeeService.findById(employeeId)).status(Response.Status.OK).build();
	}

	@GET
	@Tag(name = "get all Employees")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@BeanParam PageRequest page) {

		return Response.ok(employeeService.getAll(page)).status(Response.Status.OK).build();
	}
	
	@GET
	@Tag(name = "get all Employees Starting Letter")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/start")
	public Response getAllStart(@BeanParam PageRequest page) {

		return Response.ok(employeeService.getAllByStartingLetter(page)).status(Response.Status.OK).build();
	}

	@PUT
	@Tag(name = "update an Employee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@Path("/{employeeId}")
	public Response put(@Parameter(name = "employeeId", required = true) @PathParam("employeeId") Integer employeeId,
			@NotNull @Valid EmployeeRequest employee) {
		if (!Objects.equals(employeeId, employee.getEmployeeId())) {
			throw new ServiceException("Path variable employeeId does not match Employee.employeeId");
		}
		employeeService.update(employee);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/{employeeId}")
	public Response delete(@Parameter(name = "employeeId", required = true) @PathParam("employeeId") Integer employeeId) {

		employeeService.delete(employeeId);
		
		return Response.status(204).build();
	}
}
