package org.acme.quarkus.sample;

import java.net.URI;
import java.util.List;
import java.util.Objects;
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

@Path("/instrutors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "instrutor", description = "Instrutor Operations")
@AllArgsConstructor
public class InstrutorResource {

	private final InstrutorService instrutorService;
	private final InstrutorConverter instrutorConverter;
	private final TelefoneService telefoneService;
	private final TelefoneConverter telefoneConverter;

	@GET
	@APIResponse(responseCode = "200", description = "Get All Instrutors", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Instrutor.class)))
	public Response get() {
		return Response.ok(instrutorService.findAll()).build();
	}

	@GET
	@Path("/{instrutorId}")
	@APIResponse(responseCode = "200", description = "Get Instrutor by instrutorId", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Instrutor.class)))
	@APIResponse(responseCode = "404", description = "Instrutor does not exist for instrutorId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getById(
			@Parameter(name = "instrutorId", required = true) @PathParam("instrutorId") Integer instrutorId) {
		return instrutorService.findById(instrutorId).map(instrutor -> Response.ok(instrutor).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}

	@POST
	@APIResponse(responseCode = "201", description = "Instrutor Created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Instrutor.class)))
	@APIResponse(responseCode = "400", description = "Invalid Instrutor", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Instrutor already exists for instrutorId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response post(@NotNull @Valid Instrutor instrutor, @Context UriInfo uriInfo) {
		InstrutorEntity entity = instrutorService.save(instrutor);
		
		List<Telefone> tel = telefoneService.save(instrutor, entity).stream().map(t -> telefoneConverter.entityToDto(t)).collect(Collectors.toList());	
		instrutor = instrutorConverter.entityToDto(entity);
		if(tel != null)
			instrutor.getTelefones().addAll(tel);

		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(instrutor.getInstrutorId())).build();
		return Response.created(uri).entity(instrutor).build();
	}

	@PUT
	@Path("/{instrutorId}")
	@APIResponse(responseCode = "204", description = "Instrutor updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Instrutor.class)))
	@APIResponse(responseCode = "400", description = "Invalid Instrutor", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Instrutor object does not have instrutorId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "400", description = "Path variable instrutorId does not match Instrutor.instrutorId", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(responseCode = "404", description = "No Instrutor found for instrutorId provided", content = @Content(mediaType = MediaType.APPLICATION_JSON))
	public Response put(@Parameter(name = "instrutorId", required = true) @PathParam("instrutorId") Integer instrutorId,
			@NotNull @Valid Instrutor instrutor) {
		if (!Objects.equals(instrutorId, instrutor.getInstrutorId())) {
			throw new ServiceException("Path variable instrutorId does not match Instrutor.instrutorId");
		}
		telefoneService.update(instrutor);
		instrutorService.update(instrutor);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("/{instrutorId}")
	public void delete(@PathParam("instrutorId") Integer instrutorId) {

		instrutorService.delete(instrutorId);
	}

}