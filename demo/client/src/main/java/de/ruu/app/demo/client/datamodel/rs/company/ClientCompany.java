package de.ruu.app.demo.client.rs.company;

import de.ruu.app.demo.common.Company;
import de.ruu.app.demo.common.CompanyService;
import de.ruu.app.demo.common.Paths;
import de.ruu.app.demo.common.datamodel.dto.CompanyDTO;
import de.ruu.lib.util.jsonb.JsonbConfigurator;
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
public class ClientCompany implements CompanyService<Company>
{
	private static final String UNEXPECTED_STATUS = "unexpected status: ";

//	@Inject
//	@ConfigProperty(name = "company.rest-api.scheme", defaultValue = "http")
	private String scheme =
			ConfigProvider.getConfig().getOptionalValue("company.rest-api.scheme", String.class).orElse("http");

//	@Inject
//	@ConfigProperty(name = "company.rest-api.host"  , defaultValue = "127.0.0.1")
	private String host =
			ConfigProvider.getConfig().getOptionalValue("company.rest-api.host", String.class).orElse("127.0.0.1");

//	@Inject
//	@ConfigProperty(name = "company.rest-api.port"  , defaultValue = "8080")
	private Integer port =
			ConfigProvider.getConfig().getOptionalValue("company.rest-api.port", Integer.class).orElse(8080);

	private URI uri;

	private String templateCompany;
	private String templateCompanyWithDepartments;

	private Client client;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// lifecycle methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@PostConstruct public void postConstruct()
	{
		String schemaHostPort = scheme + "://" + host + ":" + port;

		uri = URI.create(schemaHostPort + Paths.DEMO + Paths.COMPANY);

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

	@Override public Company create(Company company)
	{
		Response response = client.target(uri).request().post(Entity.entity(company, APPLICATION_JSON));

		if (not(response.getStatus() == Status.CREATED.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return response.readEntity(CompanyDTO.class);
	}

	@Override public Optional<Company> read(Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID);
		Response  response = target.resolveTemplate("id", id).request().get();

		int status = response.getStatus();

		if (status == Status.OK.getStatusCode())
		{
			return Optional.of(response.readEntity(CompanyDTO.class));
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

	@Override public Company update(Company company)
	{
		Response response = client.target(uri).request().put(Entity.entity(company, APPLICATION_JSON));

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return response.readEntity(CompanyDTO.class);
	}

	@Override public void delete(Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID);
		Response  response = target.resolveTemplate("id", id).request().delete();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + target.getUri(), response);
		}
	}

	@Override public Set<Company> findAll()
	{
		Response response = client.target(uri).request().get();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + uri, response);
		}

		return new HashSet<>(response.readEntity(new GenericType<HashSet<CompanyDTO>>() {}));
	}

	@Override public Optional<Company> findWithDepartments(Long id)
	{
		WebTarget target   = client.target(uri + Paths.BY_ID_WITH_DEPARTMENTS);
		Response  response = target.resolveTemplate("id", id).request().get();

		if (not(response.getStatus() == Status.OK.getStatusCode()))
		{
			throw new RestClientCallException(UNEXPECTED_STATUS + response.getStatus() + "\nuri: " + target.getUri(), response);
		}

		return Optional.of(response.readEntity(CompanyDTO.class));
	}
}