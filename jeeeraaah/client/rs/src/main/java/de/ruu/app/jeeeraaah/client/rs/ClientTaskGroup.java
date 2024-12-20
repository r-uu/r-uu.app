package de.ruu.app.jeeeraaah.client.rs;

import de.ruu.app.jeeeraaah.common.Paths;
import de.ruu.app.jeeeraaah.common.TaskGroupService;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.jsonb.JsonbConfigurator;
import de.ruu.lib.util.rs.RestClientCallException;
import de.ruu.lib.util.rs.filter.logging.ClientRequestLoggingFilter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.ConfigProvider;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static de.ruu.lib.util.BooleanFunctions.not;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Slf4j
public class ClientTaskGroup implements TaskGroupService<TaskGroupEntityDTO>
{
	private static final String UNEXPECTED_STATUS = "unexpected status: ";

	private String scheme =
			ConfigProvider.getConfig().getOptionalValue("jeeeraaah.rest-api.scheme", String.class).orElse("http");

	private String host =
			ConfigProvider.getConfig().getOptionalValue("jeeeraaah.rest-api.host", String.class).orElse("127.0.0.1");

	private Integer port =
			ConfigProvider.getConfig().getOptionalValue("jeeeraaah.rest-api.port", Integer.class).orElse(9080);

	private URI uri;

	private Client client;

//	public ClientTaskGroup()
//	{
//		log.debug("-".repeat(50));
//	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// lifecycle methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@PostConstruct public void postConstruct()
	{
//		log.debug("-".repeat(50));

		String schemaHostPort = scheme + "://" + host + ":" + port;

		uri = URI.create(schemaHostPort + Paths.PATH_TO_APP + Paths.PATH_APPENDER_TO_DOMAIN_TASK_GROUP);

		log.debug("scheme        : {}", scheme);
		log.debug("host          : {}", host);
		log.debug("port          : {}", port);
		log.debug("schemaHostPort: {}", schemaHostPort);
		log.debug("uri           : {}", uri);

		client = ClientBuilder.newClient();
		client.register(new JsonbConfigurator());
		client.register(new ClientRequestLoggingFilter());
	}

	@PreDestroy public void preDestroy() { client.close(); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// interface implementations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override public TaskGroupEntityDTO create(TaskGroupEntityDTO taskGroup)
	{
		Response response = client.target(uri).request().post(Entity.entity(taskGroup, APPLICATION_JSON));

		if (not(response.getStatus() == Status.CREATED.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return response.readEntity(TaskGroupEntityDTO.class);
	}

	@Override public Optional<? extends TaskGroupEntityDTO> read(@NonNull Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID);
		Response  response = target.resolveTemplate("id", id).request().get();

		int status = response.getStatus();

		if (status == Status.OK.getStatusCode())
		{
			return Optional.of(response.readEntity(TaskGroupEntityDTO.class));
		}
		else if (status == Status.NOT_FOUND.getStatusCode())
		{
			return Optional.empty();
		}
		else
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + status + "\nuri: " + target.getUri(), response);
		}
	}

	@Override public TaskGroupEntityDTO update(TaskGroupEntityDTO taskGroup)
	{
		Response response = client.target(uri).request().put(Entity.entity(taskGroup, APPLICATION_JSON));

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return response.readEntity(TaskGroupEntityDTO.class);
	}

	@Override public void delete(@NonNull Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID);
		Response  response = target.resolveTemplate("id", id).request().delete();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + target.getUri(), response);
		}
	}

	@Override public Set<? extends TaskGroupEntityDTO> findAll()
	{
		Response response = client.target(uri).request().get();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return new HashSet<>(response.readEntity(new GenericType<HashSet<TaskGroupEntityDTO>>() {}));
	}

	@Override public Optional<? extends TaskGroupEntityDTO> findWithTasks(Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID_WITH_TASKS);
		Response  response = target.resolveTemplate("id", id).request().get();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + target.getUri(), response);
		}

		return Optional.of(response.readEntity(TaskGroupEntityDTO.class));
	}
}