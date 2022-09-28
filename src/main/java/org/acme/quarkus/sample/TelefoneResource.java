package org.acme.quarkus.sample;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;


import lombok.AllArgsConstructor;

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "telefone", description = "Telefone Operations")
@AllArgsConstructor
public class TelefoneResource {
	
	private final TelefoneService telefoneService;
	
	@GET
	@APIResponse(responseCode = "200", description = "Get All Telefones", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Telefone.class)))
	public Response get() {
		return Response.ok(telefoneService.findAll()).build();
	}
	
	@GET
	@Path("/{instrutorId}")
	public Response getByInstrutorId(@Parameter(name = "instrutorId", required = true) @PathParam("instrutorId") Integer instrutorId) {
		return Response.ok(telefoneService.findAllInstrutor(instrutorId)).build();
	}

}
