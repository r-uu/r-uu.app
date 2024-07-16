package de.ruu.app.demo.client.datamodel.rs.postaladdress;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.lib.cdi.se.CDIContainer;
import de.ruu.lib.junit.DisabledOnServerNotListening;
import jakarta.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
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

@DisabledOnServerNotListening(propertyNameHost = "postal-address.rest-api.host", propertyNamePort = "postal-address.rest-api.port")
@Slf4j
class ClientPostalAddressTest
{
	private ClientPostalAddress client;

	@BeforeAll static void beforeAll()
	{
		CDIContainer.bootstrap(ClientPostalAddressTest.class.getClassLoader());
	}

	@BeforeEach void beforeEach()
	{
		client = CDI.current().select(ClientPostalAddress.class).get();
	}

	@Test void testFindAll()
	{
		Set<PostalAddress> all = client.findAll();

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
				client.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		log.info("\nreceived postalAddress\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddress;

			assertThat(dto.id  (), is(not(nullValue())));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 0       ));

			assertThat(dto.street      (), is(street      ));
			assertThat(dto.streetNumber(), is(streetNumber));
			assertThat(dto.city        (), is(city        ));
			assertThat(dto.postalCode  (), is(postalCode  ));
			assertThat(dto.country     (), is(country     ));
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
				client.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		if (postalAddressIn instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddressIn;

			Optional<PostalAddress> optional = client.read(dto.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			PostalAddress postalAddressOut = optional.get();

			log.info("\nreceived postal address\n{}", postalAddressOut);

			if (postalAddressOut instanceof PostalAddressDTO)
			{
				dto = (PostalAddressDTO) postalAddressOut;

				assertThat(dto.id  (), is(not(nullValue())));

				assertThat(dto.version(), is(not(nullValue())));
				assertThat(dto.version(), is((short) 0       ));

				assertThat(dto.id  (), is(not(nullValue())));

				assertThat(dto.street      (), is(street      ));
				assertThat(dto.streetNumber(), is(streetNumber));
				assertThat(dto.city        (), is(city        ));
				assertThat(dto.postalCode  (), is(postalCode  ));
				assertThat(dto.country     (), is(country     ));
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

		PostalAddress postalAddressIn =
				client.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		city = "modified " + System.currentTimeMillis();

		postalAddressIn.setCity(city);

		PostalAddress postalAddressOut = client.update(postalAddressIn);

		log.info("\nreceived postal address\n{}", postalAddressOut);

		if (postalAddressOut instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddressOut;

			assertThat(dto.id(), is(not(nullValue())));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 1       ));

			assertThat(dto.street      (), is(street      ));
			assertThat(dto.streetNumber(), is(streetNumber));
			assertThat(dto.city        (), is(city        ));
			assertThat(dto.postalCode  (), is(postalCode  ));
			assertThat(dto.country     (), is(country     ));
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

		PostalAddress postalAddress =
				client.create(
						new PostalAddressEntity(street, streetNumber, city, postalCode, country));

		log.info("\nreceived postal address\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddress;

			client.delete(dto.id());

			Optional<PostalAddress> optional = client.read(dto.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(false));
		}
		else
		{
			fail("unexpected type: " + postalAddress.getClass().getName());
		}
	}
}