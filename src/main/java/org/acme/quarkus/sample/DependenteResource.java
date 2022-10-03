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

@Path("/dependente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "dependente", description = "Dependente Operations")
@AllArgsConstructor
public class DependenteResource {

	private final DependenteService dependenteService;
	private final DependenteConverter dependenteConverter;

	@GET
	@Path("/{dependenteId}")
	@APIResponse(responseCode = "200", description = "Get Dependente by dependenteId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Dependente.class)))
	@APIResponse(responseCode = "404", description = "Dependente does not exist for dependenteId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(
			@Parameter(name = "dependenteId", required = true) @PathParam("dependenteId") Integer dependenteId) {
		return dependenteService.findById(dependenteId).map(dependente -> Response.ok(dependente).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@POST
	@APIResponse(responseCode = "201", description = "Dependente Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Dependente.class)))
	@APIResponse(responseCode = "400", description = "Invalid Dependente", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Dependente already exists for dependenteId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Dependente dependente, @Context UriInfo uriInfo) {
		DependenteEntity entity = dependenteService.save(dependente);
		dependente = dependenteConverter.entityToDto(entity);
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(dependente.getDependenteId())).build();
		return Response.created(uri).entity(dependente).build();
	}

}
