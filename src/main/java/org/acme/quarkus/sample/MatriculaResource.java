package org.acme.quarkus.sample;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import lombok.AllArgsConstructor;

@Path("/matricula")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "matricula", description = "Matricula Operations")
@AllArgsConstructor
public class MatriculaResource {
	
	private final MatriculaService matriculaService;
	private final MatriculaConverter matriculaConverter;
	
	@POST
	@APIResponse(responseCode = "201", description = "Matricula Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = MatriculaEntity.class)))
	@APIResponse(responseCode = "400", description = "Invalid Matricula", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Matricula already exists for alunoId and turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Matricula matricula, @Context UriInfo uriInfo) {
		MatriculaEntity entity = matriculaService.save(matricula);
		matricula = matriculaConverter.entityToDto(entity);
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(matricula.getAlunoId()) + "/" + Integer.toString(matricula.getTurmaId())).build();
		return Response.created(uri).entity(matricula).build();
	}
	
	@GET
	@Path("/aluno/{alunoId}/turma/{turmaId}")
	@APIResponse(responseCode = "200", description = "Get Matricula by alunoId and turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Matricula.class)))
	@APIResponse(responseCode = "404", description = "Matricula does not exist for alunoId and turmaId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(@Parameter(name = "alunoId", required = true) @PathParam("alunoId") Integer alunoId,
			@Parameter(name = "turmaId", required = true) @PathParam("turmaId") Integer turmaId) {
		return matriculaService.findById(alunoId, turmaId).map(matricula -> Response.ok(matricula).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

}
