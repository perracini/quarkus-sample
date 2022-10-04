package org.acme.quarkus.sample;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

@Path("/titular")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "titular", description = "Titular Operations")
@AllArgsConstructor
public class TitularResource {

	private final TitularService titularService;
	private final DependenteService dependenteService;

	@GET
	@Path("/{titularId}")
	@APIResponse(responseCode = "200", description = "Get Titular by titularId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Titular.class)))
	@APIResponse(responseCode = "404", description = "Titular does not exist for titularId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(@Parameter(name = "titularId", required = true) @PathParam("titularId") Integer titularId) {
		return titularService.findById(titularId).map(titular -> Response.ok(titular).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@POST
	@APIResponse(responseCode = "201", description = "Titular Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Titular.class)))
	@APIResponse(responseCode = "400", description = "Invalid Titular", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Titular already exists for titularId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Titular titular, @Context UriInfo uriInfo) {
		TitularEntity entity = titularService.save(titular);
		Titular toReturn = titularService.converter(Optional.ofNullable(entity));
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(toReturn.getTitularId())).build();
		return Response.created(uri).entity(toReturn).build();
	}
	
	@DELETE
	@Path("/{titularId}")
	public void delete(@PathParam("titularId") Integer titularId) {
		titularService.delete(titularId);
	}
	
	@PUT
	@Path("/{titularId}")
	@APIResponse(responseCode = "204", description = "Titular updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Titular.class)))
	@APIResponse(responseCode = "400", description = "Invalid Titular", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Titular object does not have titularId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Path variable titularId does not match Titular.titularId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Titular found for titularId provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response put(@Parameter(name = "titularId", required = true) @PathParam("titularId") Integer titularId,
			@NotNull @Valid Titular titular) {
		if (!Objects.equals(titularId, titular.getTitularId())) {
			throw new ServiceException("Path variable titularId does not match Titular.titularId");
		}
		dependenteService.updateDependentesFromTitular(titular);
		titularService.update(titular);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
   	@GET
	@APIResponse(responseCode = "200", description = "Get All Titulars", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Titular.class)))
	public Response get() {
		return Response.ok(titularService.findAll()).build();
	}

}
