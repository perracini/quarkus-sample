package org.acme.quarkus.sample;

import java.net.URI;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.acme.quarkus.exception.ServiceException;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import lombok.AllArgsConstructor;

@Path("/turma")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "turma", description = "Turma Operations")
@AllArgsConstructor
public class TurmaResource {

	private final TurmaService turmaService;
	private final TurmaConverter turmaConverter;

	@GET
	@APIResponse(responseCode = "200", description = "Get All Turmas", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Turma.class)))
	public Response get() {
		return Response.ok(turmaService.findAll()).build();
	}

	@GET
	@Path("/instrutor/{instrutorId}")
	public Response getByInstrutorId(
			@Parameter(name = "instrutorId", required = true) @PathParam("instrutorId") Integer instrutorId) {
		return Response.ok(turmaService.getTurmasWithInstrutor(instrutorId)).build();
	}

	@GET
	@Path("/{turmaId}")
	@APIResponse(responseCode = "200", description = "Get Turma by turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Turma.class)))
	@APIResponse(responseCode = "404", description = "Turma does not exist for turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(@Parameter(name = "turmaId", required = true) @PathParam("turmaId") Integer turmaId) {
		return turmaService.findById(turmaId).map(turma -> Response.ok(turma).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@POST
	@APIResponse(responseCode = "201", description = "Turma Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Turma.class)))
	@APIResponse(responseCode = "400", description = "Invalid Turma", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Turma already exists for turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Turma turma, @Context UriInfo uriInfo) {
		TurmaEntity entity = turmaService.save(turma);
		turma = turmaConverter.entityToDto(entity);
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(turma.getTurmaId())).build();
		return Response.created(uri).entity(turma).build();
	}

	@PUT
	@Path("/{turmaId}")
	@APIResponse(responseCode = "204", description = "Turma updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Turma.class)))
	@APIResponse(responseCode = "400", description = "Invalid Turma", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Turma object does not have turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Path variable turmaId does not match Turma.turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Turma found for turmaId provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response put(@Parameter(name = "turmaId", required = true) @PathParam("turmaId") Integer turmaId,
			@NotNull @Valid Turma turma) {
		if (!Objects.equals(turmaId, turma.getTurmaId())) {
			throw new ServiceException("Path variable turmaId does not match Turma.turmaId");
		}
		turmaService.update(turma);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@GET
	@Path("/teste")
	@APIResponse(responseCode = "200", description = "Get All Turmas Not Finished", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Turma.class)))
	public Response getTeste() {
		return Response.ok(turmaService.getTurmasEncerradas()).build();
	}

}
