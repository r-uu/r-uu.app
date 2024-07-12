package de.ruu.app.demo.client.datamodel.rs.postaladdress;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.dto.PostalAddressDTO;
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

		log.info("\nreceived {} tag groups", all.size());
	}

	@Test void testCreate()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddress postalAddress =
				client.create(
						PostalAddressDTO
								.builder()
										.city(name)
								.build());

		log.info("\nreceived postalAddress\n{}", postalAddress);

		if (postalAddress instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddress;

			assertThat(dto.id  (), is(not(nullValue())));
			assertThat(dto.city(), is(name));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 0       ));
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
				client.create(
						PostalAddressDTO
								.builder()
								.city(name)
								.build());

		if (postalAddressIn instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddressIn;

			Optional<PostalAddress> optional = client.read(dto.id());

			assertThat(optional            , is(not(nullValue())));
			assertThat(optional.isPresent(), is(not(false)));

			PostalAddress postalAddressOut = optional.get();

			log.info("\nreceived postal address\n{}" + postalAddressOut);

			if (postalAddressOut instanceof PostalAddressDTO)
			{
				dto = (PostalAddressDTO) postalAddressOut;

				assertThat(dto.id  (), is(not(nullValue())));
				assertThat(dto.city(), is(name));

				assertThat(dto.version(), is(not(nullValue())));
				assertThat(dto.version(), is((short) 0       ));
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

		PostalAddress postalAddressIn = client.create(PostalAddressDTO.builder().city(name).build());

		name = "modified " + System.currentTimeMillis();

		postalAddressIn.setCity(name);

		PostalAddress postalAddressOut = client.update(postalAddressIn);

		log.info("\nreceived postal address\n{}" + postalAddressOut);

		if (postalAddressOut instanceof PostalAddressDTO)
		{
			PostalAddressDTO dto = (PostalAddressDTO) postalAddressOut;

			assertThat(dto.id(), is(not(nullValue())));

			assertThat(dto.version(), is(not(nullValue())));
			assertThat(dto.version(), is((short) 1       ));

			assertThat(dto.city(), is(name));
		}
		else
		{
			fail("unexpected type: " + postalAddressOut.getClass().getName());
		}
	}

	@Test void testDelete()
	{
		String name = "de/ruu/app/demo/client/datamodel/rs/postaladdress " + System.currentTimeMillis();

		PostalAddress postalAddress = client.create(PostalAddressDTO.builder().city(name).build());;

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