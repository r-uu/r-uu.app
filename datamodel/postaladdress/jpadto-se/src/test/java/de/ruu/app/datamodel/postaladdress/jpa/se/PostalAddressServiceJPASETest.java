package de.ruu.app.datamodel.postaladdress.jpa.se;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
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

		log.info("\nreceived {} postal addresses", all.size());
	}

	@Test void testCreate()
	{
		String street       = "street "       + System.currentTimeMillis();
		String streetNumber = "streetNumber " + System.currentTimeMillis();
		String city         = "city "         + System.currentTimeMillis();
		String postalCode   = "postalCode "   + System.currentTimeMillis();
		String country      = "country "      + System.currentTimeMillis();

		PostalAddress postalAddress =
				service.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		log.info("\nreceived postalAddress\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddress;

			assertThat(entity.id  (), is(not(nullValue())));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 0       ));

			assertThat(entity.street      (), is(street      ));
			assertThat(entity.streetNumber(), is(streetNumber));
			assertThat(entity.city        (), is(city        ));
			assertThat(entity.postalCode  (), is(postalCode  ));
			assertThat(entity.country     (), is(country     ));
		}
		else
		{
			fail("unexpected type: " + postalAddress.getClass().getName());
		}
	}

	@Test void testRead()
	{
		String street       = "street "       + System.currentTimeMillis();
		String streetNumber = "streetNumber " + System.currentTimeMillis();
		String city         = "city "         + System.currentTimeMillis();
		String postalCode   = "postalCode "   + System.currentTimeMillis();
		String country      = "country "      + System.currentTimeMillis();

		PostalAddress postalAddressIn =
				service.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

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

				assertThat(entity.version(), is(not(nullValue())));
				assertThat(entity.version(), is((short) 0       ));

				assertThat(entity.id  (), is(not(nullValue())));

				assertThat(entity.street      (), is(street      ));
				assertThat(entity.streetNumber(), is(streetNumber));
				assertThat(entity.city        (), is(city        ));
				assertThat(entity.postalCode  (), is(postalCode  ));
				assertThat(entity.country     (), is(country     ));
			}
			else
			{
				fail("unexpected type: " + postalAddressOut.getClass().getName());
			}
		}
	}

	@Test void testUpdate()
	{
		String street       = "street "       + System.currentTimeMillis();
		String streetNumber = "streetNumber " + System.currentTimeMillis();
		String city         = "city "         + System.currentTimeMillis();
		String postalCode   = "postalCode "   + System.currentTimeMillis();
		String country      = "country "      + System.currentTimeMillis();

		PostalAddressEntity postalAddressIn =
				service.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		city = "modified " + System.currentTimeMillis();

		postalAddressIn.setCity(city);

		PostalAddress postalAddressOut = service.update(postalAddressIn);

		log.info("\nreceived postal address\n{}" + postalAddressOut);

		if (postalAddressOut instanceof PostalAddressEntity)
		{
			PostalAddressEntity entity = (PostalAddressEntity) postalAddressOut;

			assertThat(entity.id(), is(not(nullValue())));

			assertThat(entity.version(), is(not(nullValue())));
			assertThat(entity.version(), is((short) 1       ));

			assertThat(entity.street      (), is(street      ));
			assertThat(entity.streetNumber(), is(streetNumber));
			assertThat(entity.city        (), is(city        ));
			assertThat(entity.postalCode  (), is(postalCode  ));
			assertThat(entity.country     (), is(country     ));
		}
		else
		{
			fail("unexpected type: " + postalAddressOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		String street       = "street "       + System.currentTimeMillis();
		String streetNumber = "streetNumber " + System.currentTimeMillis();
		String city         = "city "         + System.currentTimeMillis();
		String postalCode   = "postalCode "   + System.currentTimeMillis();
		String country      = "country "      + System.currentTimeMillis();

		PostalAddressEntity postalAddress =
				service.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

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