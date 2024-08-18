package de.ruu.app.jeee_raaa.server;

import de.ruu.app.jeee_raaa.common.Task;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import static de.ruu.app.jeee_raaa.common.Paths.PATH_TO_APP;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
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
@Path(PATH_TO_APP)
@OpenAPIDefinition(info = @Info(version = "a version", title = "a title"))
@Timed
public class JEEE_RAAA
{
	@Inject private TaskServiceNEO service;

	@GET
	@Produces(APPLICATION_JSON)
	public Response findAll()
	{
		return ok(service.findAll()).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response create(Task task)
	{
		return status(CREATED).entity(task).build();
	}
}