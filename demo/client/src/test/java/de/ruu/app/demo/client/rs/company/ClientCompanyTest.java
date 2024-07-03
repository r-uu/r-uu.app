package de.ruu.app.demo.client.rs.company;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;
import java.util.Set;

import de.ruu.app.demo.common.Company;
import de.ruu.app.demo.common.datamodel.dto.CompanyDTO;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ruu.lib.cdi.se.CDIContainer;
import jakarta.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;

@DisabledOnServerNotListening(propertyNameHost = "company.rest-api.host", propertyNamePort = "company.rest-api.port")
@Slf4j
class ClientCompanyTest
{
	private ClientCompany client;

	@BeforeAll static void beforeAll()
	{
		CDIContainer.bootstrap(ClientCompanyTest.class.getClassLoader());
	}

	@BeforeEach void beforeEach()
	{
		client = CDI.current().select(ClientCompany.class).get();
	}

	@Test void testFindAll()
	{
		Set<Company> all = client.findAll();

		assertThat(all, is(not(nullValue())));

		log.info("\nreceived {} tag groups", all.size());
	}

	@Test void testCreate()
	{
		String name = "company " + System.currentTimeMillis();

		Company company = client.create(new CompanyDTO(name));

		log.info("\nreceived company\n{}", company);

		if (company instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) company;

			assertThat(dto.id  (), is(not(nullValue())));
			assertThat(dto.name(), is(name));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 1       ));

			assertThat(dto.optionalDepartments()            , is(not(nullValue())));
			assertThat(dto.optionalDepartments().isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + company.getClass().getName());
		}
	}

	@Test void testRead()
	{
		String name = "company " + System.currentTimeMillis();

		Company companyIn = client.create(new CompanyDTO(name));

		Optional<Company> optional = client.read(companyIn.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(not(false)));

		Company companyOut = optional.get();

		log.info("\nreceived company\n{}" + companyOut);

		if (companyOut instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) companyOut;

			assertThat(dto.id  (), is(not(nullValue())));
			assertThat(dto.name(), is(name));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 1       ));

			assertThat(dto.optionalDepartments()            , is(not(nullValue())));
			assertThat(dto.optionalDepartments().isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + companyOut.getClass().getName());
		}
	}

	@Test void testUpdate()
	{
		Company companyIn = client.create(new CompanyDTO("company " + System.currentTimeMillis()));

		String name = "modified " + System.currentTimeMillis();

		companyIn.name(name);

		Company companyOut = client.update(companyIn);

		log.info("\nreceived company\n{}" + companyOut);

		if (companyOut instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) companyOut;

			assertThat(dto.id(), is(not(nullValue())));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 1       ));

			assertThat(dto.optionalDepartments()            , is(not(nullValue())));
			assertThat(dto.optionalDepartments().isPresent(), is(false));

			assertThat(dto.name(), is(name));
		}
		else
		{
			fail("unexpected type: " + companyOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		Company company = client.create(new CompanyDTO("company " + System.currentTimeMillis()));

		log.info("\nreceived company\n{}", company);

		client.delete(company.id());

		Optional<Company> optional = client.read(company.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(false));
	}
}