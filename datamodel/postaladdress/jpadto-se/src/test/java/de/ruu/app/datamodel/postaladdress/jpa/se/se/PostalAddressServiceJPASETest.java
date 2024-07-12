package de.ruu.app.datamodel.postaladdress.jpa.se.se;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpa.se.PostalAddressServiceJPASE;
import de.ruu.lib.cdi.common.CDIExtension;
import de.ruu.lib.cdi.se.CDIContainer;
import de.ruu.lib.jpa.se.TransactionalInterceptorCDI;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisabledOnServerNotListening
(
		propertyNameHost = "de.ruu.lib.jpa.se.hibernate.postgres.AbstractEntityManagerProducer.dbhost",
		propertyNamePort = "de.ruu.lib.jpa.se.hibernate.postgres.AbstractEntityManagerProducer.dbport"
)
@Slf4j
class PostalAddressServiceJPASETest
{
	private static SeContainer seContainer; // initialisation and closure handled in before/after all methods

	private PostalAddressServiceJPASE service; // initialisation handled in before each method

	@SuppressWarnings("unchecked")
	@BeforeAll static void beforeAll()
	{
		log.debug("cdi container initialisation");
		try
		{
			seContainer =
					SeContainerInitializer
							.newInstance()
							.addExtensions     (CDIExtension.class               )
							.addBeanClasses    (TransactionalInterceptorCDI.class)
							.addBeanClasses    (EntityManagerProducer.class      )
							.enableInterceptors(TransactionalInterceptorCDI.class)
							.initialize();
			CDIContainer.bootstrap(PostalAddressServiceJPASETest.class.getClassLoader());
		}
		catch (Exception e)
		{
			log.error("failure initialising seContainer", e);
		}
		log.debug("cdi container initialisation {}", seContainer == null ? "unsuccessful" : "successful");
	}

	@AfterAll
	static void afterAll()
	{
		log.debug("cdi container shut down");
		seContainer.close();
		log.debug("cdi container shut down {}", seContainer.isRunning() ? "unsuccessful" : "successful");
	}

	@BeforeEach
	void beforeEach()
	{
		service = seContainer.select(PostalAddressServiceJPASE.class).get();
	}

	@Test void testFindAll()
	{
		Set<PostalAddressEntity> all = service.findAll();

		assertThat(all, is(not(nullValue())));

		log.info("\nreceived {} tag groups", all.size());
	}

	@Test void testCreate()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddress postalAddress =
				service.create(
						PostalAddressEntity
								.builder()
										.city(name)
								.build());

		log.info("\nreceived postalAddress\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddress;

			assertThat(entity.id  (), is(not(nullValue())));
			assertThat(entity.city(), is(name));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 0       ));
		}
		else
		{
			fail("unexpected type: " + postalAddress.getClass().getName());
		}
	}

	@Test void testRead()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddress postalAddressIn =
				service.create(
						PostalAddressEntity
								.builder()
								.city(name)
								.build());

		if (postalAddressIn instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddressIn;

			Optional<PostalAddressEntity> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			PostalAddress postalAddressOut = optional.get();

			log.info("\nreceived postal address\n{}" + postalAddressOut);

			if (postalAddressOut instanceof PostalAddressEntity)
			{
				entity = (PostalAddressEntity) postalAddressOut;

				assertThat(entity.id  (), is(not(nullValue())));
				assertThat(entity.city(), is(name));

				assertThat(entity.version(), is(not(nullValue())));
				assertThat(entity.version(), is((short) 0       ));
			}
			else
			{
				fail("unexpected type: " + postalAddressOut.getClass().getName());
			}
		}
	}

	@Test void testUpdate()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddressEntity postalAddressIn = service.create(PostalAddressEntity.builder().city(name).build());

		name = "modified " + System.currentTimeMillis();

		postalAddressIn.setCity(name);

		PostalAddress postalAddressOut = service.update(postalAddressIn);

		log.info("\nreceived postal address\n{}" + postalAddressOut);

		if (postalAddressOut instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddressOut;

			assertThat(entity.id(), is(not(nullValue())));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 1       ));

			assertThat(entity.city(), is(name));
		}
		else
		{
			fail("unexpected type: " + postalAddressOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddressEntity postalAddress = service.create(PostalAddressEntity.builder().city(name).build());;

		log.info("\nreceived postal address\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddress;

			service.delete(entity.id());

			Optional<PostalAddressEntity> optional = service.read(entity.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + postalAddress.getClass().getName());
		}
	}
}