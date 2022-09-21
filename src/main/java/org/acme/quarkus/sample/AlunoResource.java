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

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "aluno", description = "Aluno Operations")
@AllArgsConstructor
public class AlunoResource {

	private final AlunoService alunoService;
	private final AlunoConverter alunoConverter;

	@GET
	@APIResponse(responseCode = "200", description = "Get All Alunos", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Aluno.class)))
	public Response get() {
		return Response.ok(alunoService.findAll()).build();
	}

	@GET
	@Path("/{alunoId}")
	@APIResponse(responseCode = "200", description = "Get Aluno by alunoId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Aluno.class)))
	@APIResponse(responseCode = "404", description = "Aluno does not exist for alunoId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(@Parameter(name = "alunoId", required = true) @PathParam("alunoId") Integer alunoId) {
		return alunoService.findById(alunoId).map(aluno -> Response.ok(aluno).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@GET
	@Path("/{pageNumber}/{pageSize}")
	public Response getPage(@Parameter(name = "pageNumber", required = true) @PathParam("pageNumber") int pageNumber,
			@Parameter(name = "pageSize", required = true) @PathParam("pageSize") int pageSize) {
		return Response.ok(alunoService.findPage(pageNumber, pageSize)).build();

	}

	@POST
	@APIResponse(responseCode = "201", description = "Aluno Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Aluno.class)))
	@APIResponse(responseCode = "400", description = "Invalid Aluno", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Aluno already exists for alunoId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Aluno aluno, @Context UriInfo uriInfo) {
		AlunoEntity entity = alunoService.save(aluno);
		aluno = alunoConverter.entityToDto(entity);
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(aluno.getAlunoId())).build();
		return Response.created(uri).entity(aluno).build();
	}

	@PUT
	@Path("/{alunoId}")
	@APIResponse(responseCode = "204", description = "Aluno updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Aluno.class)))
	@APIResponse(responseCode = "400", description = "Invalid Aluno", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Aluno object does not have alunoId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Path variable alunoId does not match Aluno.alunoId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Aluno found for alunoId provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response put(@Parameter(name = "alunoId", required = true) @PathParam("alunoId") Integer alunoId,
			@NotNull @Valid Aluno aluno) {
		if (!Objects.equals(alunoId, aluno.getAlunoId())) {
			throw new ServiceException("Path variable alunoId does not match Aluno.alunoId");
		}
		alunoService.update(aluno);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}