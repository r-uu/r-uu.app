package de.ruu.app.demo.server.datamodel.postaladdress;

import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressServiceJPA;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import java.util.Optional;
import java.util.stream.Collectors;

import static de.ruu.app.demo.common.Paths.BY_ID;
import static de.ruu.app.demo.common.Paths.POSTAL_ADDRESS;
import static de.ruu.lib.util.BooleanFunctions.not;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.ok;
import static jakarta.ws.rs.core.Response.status;

/**
 * REST controller providing REST endpoints.
 * <p>
 * Methods accept DTO2 parameters, transform DTOs to entities, delegate to {@link #service} and transform entity
 * return values from {@link #service} back to DTOs. The transformations from entities to DTOs are
 * intentionally done here after transactions were committed in {@link #service}. This ensures that version
 * attributes of DTOs are respected with their new values after commit in returned DTOs.
 *
 * @author r-uu
 */

@RequestScoped
@Path(POSTAL_ADDRESS)
@OpenAPIDefinition(info = @Info(version = "a version", title = "a title"))
@Timed
public class PostalAddress
{
	@Inject private PostalAddressServiceJPA service;

	@GET
	@Produces(APPLICATION_JSON)
	public Response findAll()
	{
		return
				ok
						(
								service
										.findAll()
										.stream()
										.map(entity -> entity.toTarget())
										.collect(Collectors.toSet())
						)
						.build();
	}

	@GET
	@Path(BY_ID)
	@Produces(APPLICATION_JSON)
	public Response find(@PathParam("id") Long id)
	{
		Optional<PostalAddressEntity> result = service.read(id);
		if (not(result.isPresent()))
				return status(NOT_FOUND).entity("address with id " + id + " not found").build();
		else
			return
					ok
							(
									result
											.get()
											.toTarget()
							).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response create(PostalAddressDTO dto)
	{
		return
				status(CREATED)
						.entity
								(
										service
												.create(dto.toSource())
												.toTarget()
								).build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response update(PostalAddressDTO dto)
	{
		return
				ok
						(
								service
										.create(dto.toSource())
										.toTarget()
						).build();
	}

	@DELETE
	@Path(BY_ID)
	@Produces(APPLICATION_JSON)
	public Response delete(@PathParam("id") Long id)
	{
		try
		{
			service.delete(id);
		}
		catch (Exception e)
		{
			return status(CONFLICT).build();
		}

		return ok().build();
	}
}