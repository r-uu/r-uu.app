package de.ruu.app.demo.client.datamodel.rs.company;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.dto.CompanyDTO;
import de.ruu.app.datamodel.company.dto.DepartmentDTO;
import de.ruu.lib.cdi.se.CDIContainer;
import jakarta.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

//@DisabledOnServerNotListening(propertyNameHost = "company.rest-api.host", propertyNamePort = "company.rest-api.port")
@Disabled
@Slf4j
class ClientCompanyTest
{
	private ClientCompany    client;
	private ClientDepartment clientDepartment;

	@BeforeAll static void beforeAll()
	{
		CDIContainer.bootstrap(ClientCompanyTest.class.getClassLoader());
	}

	@BeforeEach void beforeEach()
	{
		client           = CDI.current().select(ClientCompany   .class).get();
		clientDepartment = CDI.current().select(ClientDepartment.class).get();
	}

	@Test void testFindAll()
	{
		Set<Company> all = client.findAll();

		assertThat(all, is(not(nullValue())));

		log.debug("\nreceived {} companies", all.size());
	}

	@Test void testCreate()
	{
		String name = "name " + System.currentTimeMillis();

		Company company = client.create(new CompanyDTO(name));

		log.debug("\nreceived company\n{}", company);

		if (company instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) company;

			assertThat(dto.id  (), is(not(nullValue())));
			assertThat(dto.name(), is(name));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 0       ));

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
		String name = "name " + System.currentTimeMillis();

		Company companyIn = client.create(new CompanyDTO(name));

		Optional<Company> optional = client.read(companyIn.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(not(false)));

		Company companyOut = optional.get();

		log.debug("\nreceived company\n{}" + companyOut);

		if (companyOut instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) companyOut;

			assertThat(dto.id  (), is(not(nullValue())));
			assertThat(dto.name(), is(name));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 0       ));

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
		Company companyIn = client.create(new CompanyDTO("name " + System.currentTimeMillis()));

		String name = "modified " + System.currentTimeMillis();

		companyIn.name(name);

		Company companyOut = client.update(companyIn);

		log.debug("\nreceived company\n{}" + companyOut);

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
		Company company = client.create(new CompanyDTO("name " + System.currentTimeMillis()));

		log.debug("\nreceived company\n{}", company);

		client.delete(company.id());

		Optional<Company> optional = client.read(company.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(false));
	}

	@Test void testFindWithDepartments()
	{
		Company company = client.create(new CompanyDTO("name " + System.currentTimeMillis()));

		log.debug("\nreceived company\n{}", company);

		if (company instanceof CompanyDTO)
		{
			CompanyDTO dto = (CompanyDTO) company;

			for (int i = 0; i < 3; i++)
			{
				Department department = clientDepartment.create(new DepartmentDTO(dto, "test" + i));
				log.debug("\nreceived department\n{}", department);
			}

			Optional<Company> optional = client.findWithDepartments(company.id());

			assertThat(optional.isPresent(), is(true));

			company = optional.get();

			assertThat(company.departments().isPresent() , is(true));
			assertThat(company.departments().get().size(), is(3));

			for (Department department : company.departments().get())
			{
				log.debug("company for department {} is null {}", department, department.getCompany() == null);
			}

		}
	}
}