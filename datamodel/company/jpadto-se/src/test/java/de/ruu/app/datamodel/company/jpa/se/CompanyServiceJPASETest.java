package de.ruu.app.datamodel.company.jpa.se;

import de.ruu.app.datamodel.company.Company;
import de.ruu.app.datamodel.company.Department;
import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.lib.cdi.common.CDIExtension;
import de.ruu.lib.cdi.se.CDIContainer;
import de.ruu.lib.jpa.se.TransactionalInterceptorCDI;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import jakarta.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisabledOnServerNotListening(propertyNameHost = "database.host", propertyNamePort = "database.port")
@Slf4j
class CompanyServiceJPASETest
{
//	private static SeContainer seContainer; // initialisation and closure handled in before/after all methods

	private CompanyServiceJPASE service; // initialisation handled in before each method

	@SuppressWarnings("unchecked")
	@BeforeAll static void beforeAll()
	{
		log.debug("cdi container initialisation");
		CDIContainer.bootstrap
				(
						  CompanyServiceJPASETest.class.getClassLoader()
						, List.of(TransactionalInterceptorCDI.class)
						, List.of(CDIExtension.class)
						, List.of(EntityManagerProducer.class, TransactionalInterceptorCDI.class)
				);
//		try
//		{
//			seContainer =
//					SeContainerInitializer
//							.newInstance()
//							.addExtensions     (CDIExtension.class               )
//							.addBeanClasses    (TransactionalInterceptorCDI.class)
//							.addBeanClasses    (EntityManagerProducer.class      )
//							.enableInterceptors(TransactionalInterceptorCDI.class)
//							.initialize();
//		}
//		catch (Exception e)
//		{
//			log.error("failure initialising seContainer", e);
//		}
//		log.debug("cdi container initialisation {}", seContainer == null ? "unsuccessful" : "successful");
	}

	@AfterAll
	static void afterAll()
	{
//		log.debug("cdi container shut down");
//		seContainer.close();
//		log.debug("cdi container shut down {}", seContainer.isRunning() ? "unsuccessful" : "successful");
	}

	@BeforeEach
	void beforeEach()
	{
		service = CDI.current().select(CompanyServiceJPASE.class).get();
	}

	@Test void testFindAll()
	{
		Set<CompanyEntity> all = service.findAll();

		assertThat(all, is(not(nullValue())));

		log.info("\nreceived {} companies", all.size());
	}

	@Test void testCreate()
	{
		String name = "name " + System.currentTimeMillis();

		Company company = service.create(new CompanyEntity(name));

		log.info("\nreceived company\n{}", company);

		if (company instanceof CompanyEntity)
		{
			CompanyEntity entity = (CompanyEntity) company;

			assertThat(entity.id  (), is(not(nullValue())));
			assertThat(entity.name(), is(name));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 0       ));
		}
		else
		{
			fail("unexpected type: " + company.getClass().getName());
		}
	}

	@Test void testRead()
	{
		String name = "name " + System.currentTimeMillis();

		Company companyIn = service.create(new CompanyEntity(name));

		if (companyIn instanceof CompanyEntity)
		{
			CompanyEntity entity = (CompanyEntity) companyIn;

			Optional<CompanyEntity> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			CompanyEntity entityOut = optional.get();

			log.info("\nreceived company\n{}" + entityOut);

			if (entityOut instanceof CompanyEntity)
			{
				entity = (CompanyEntity) entityOut;

				assertThat(entity.id  (), is(not(nullValue())));
				assertThat(entity.name(), is(name));

				assertThat(entity.version(), is(not(nullValue())));
				assertThat(entity.version(), is((short) 0       ));
			}
			else
			{
				fail("unexpected type: " + entityOut.getClass().getName());
			}
		}
	}

	@Test void testUpdate()
	{
		String name = "name " + System.currentTimeMillis();

		CompanyEntity companyIn = service.create(new CompanyEntity(name));

		name = "modified " + System.currentTimeMillis();

		companyIn.name(name);

		Company companyOut = service.update(companyIn);

		log.info("\nreceived company\n{}" + companyOut);

		if (companyOut instanceof CompanyEntity)
		{
			CompanyEntity entity = (CompanyEntity) companyOut;

			assertThat(entity.id(), is(not(nullValue())));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 1       ));

			assertThat(entity.name(), is(name));
		}
		else
		{
			fail("unexpected type: " + companyOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		String name = "name " + System.currentTimeMillis();

		Company company = service.create(new CompanyEntity(name));

		log.info("\nreceived company\n{}", company);

		if (company instanceof CompanyEntity)
		{
			CompanyEntity entity = (CompanyEntity) company;

			service.delete(entity.id());

			Optional<CompanyEntity> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + company.getClass().getName());
		}
	}

	@Test void testFindWithDepartments()
	{
		DepartmentServiceJPASE departmentService = CDI.current().select(DepartmentServiceJPASE.class).get();

		String name = "name " + System.currentTimeMillis();

		CompanyEntity company = service.create(new CompanyEntity(name));

		log.info("\nreceived company\n{}", company);

		DepartmentEntity department = new DepartmentEntity(company, name);

		department = departmentService.create(department);

		Optional<CompanyEntity> optional = service.findWithDepartments(company.id());

		assertThat(optional            , is(not(nullValue())));
		assertThat(optional.isPresent(), is(true));

		company = optional.get();
		Optional<Set<Department>> optionalInner = company.departments();

		assertThat(optionalInner            , is(not(nullValue())));
		assertThat(optionalInner.isPresent(), is(true));

		Set<Department> departments = optionalInner.get();

		assertThat(departments.size(), is(1));

		for (Department departmentInner : departments)
		{
			log.debug("found department {}", departmentInner);
		}
	}
}