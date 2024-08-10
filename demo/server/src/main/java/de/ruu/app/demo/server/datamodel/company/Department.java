package de.ruu.app.demo.server.datamodel.company;

import de.ruu.app.datamodel.company.dto.DepartmentDTO;
import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.CompanyServiceJPA;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentServiceJPA;
import de.ruu.app.demo.common.DepartmentCreateDataDTO;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.ruu.app.demo.common.Paths.BY_ID;
import static de.ruu.app.demo.common.Paths.DEPARTMENT;
import static de.ruu.lib.util.BooleanFunctions.not;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.created;
import static jakarta.ws.rs.core.Response.ok;
import static jakarta.ws.rs.core.Response.status;

/**
 * REST controller providing REST endpoints.
 * <p>
 * Methods accept DTO2 parameters, transform DTOs to entities, delegate to {@link #departmentService} and transform entity
 * return values from {@link #departmentService} back to DTOs. The transformations from entities to DTOs are
 * intentionally done here after transactions were committed in {@link #departmentService}. This ensures that version
 * attributes of DTOs are respected with their new values after commit in returned DTOs.
 *
 * @author r-uu
 */
@Slf4j
@RequestScoped
@Path(DEPARTMENT)
@OpenAPIDefinition(info = @Info(version = "a version", title = "a title"))
@Timed
public class Department
{
	@Inject private    CompanyServiceJPA    companyService;
	@Inject private DepartmentServiceJPA departmentService;

	@GET
//	@Path(COMPANY)
	@Produces(APPLICATION_JSON)
	public Response findAll()
	{
		return
				ok
						(
								departmentService
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
	public Response find(@PathParam("id") @NonNull Long id)
	{
		Optional<DepartmentEntity> result = departmentService.read(id);
		if (not(result.isPresent()))
				return status(NOT_FOUND).entity("department with id " + id + " not found").build();
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
	public Response create(@NonNull DepartmentCreateDataDTO data, @Context UriInfo uriInfo)
	{
		log.debug(
				"about to create department\n{}\nfor company with id {}",
				data.getDepartment(), data.getCompanyId());

		Optional<CompanyEntity> optional = companyService.read(data.getCompanyId());

		log.debug("fetched company with id {} successfully {}", data.getCompanyId(), optional.isPresent());

		if (optional.isPresent() == false) return status(NOT_FOUND).build();

		CompanyEntity company = optional.get();

		log.debug("fetched company\n{}", company);

		DepartmentEntity result = new DepartmentEntity(company, data.getDepartment().getName());

		result = departmentService.create(result);

		log.debug("created department\n{}", result);

//		// build result without location header
//		return
//				status(CREATED)
//						.entity(result.toTarget())
//						.build();

		// build result with location header
		URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(result.id())).build();

		return
				created(uri)
						.entity(result.toTarget())
						.build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response update(DepartmentDTO dto)
	{
		return
				ok
						(
								departmentService
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
			departmentService.delete(id);
		}
		catch (Exception e)
		{
			return status(CONFLICT).build();
		}

		return ok().build();
	}
}