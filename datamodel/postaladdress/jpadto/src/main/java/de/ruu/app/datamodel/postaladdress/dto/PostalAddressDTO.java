package de.ruu.app.datamodel.postaladdress.dto;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Setter
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@NoArgsConstructor (access = AccessLevel.PROTECTED, force = true) // generate no  args constructor for jsonb, jaxb, mapstruct, ...
//@RequiredArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PROTECTED)               // generate all args constructor for builder
//@Builder
public class PostalAddressDTO extends AbstractMappedDTO<PostalAddressEntity> implements PostalAddress
{
	@NonNull
//	@Builder.Default
	private String street;
	@NonNull
//	@Builder.Default
	private String streetNumber;
	@NonNull
//	@Builder.Default
	private String city;
//	@NonNull
//	@Builder.Default
	private String stateOrProvince;
	@NonNull
//	@Builder.Default
	private String postalCode;
	@NonNull
//	@Builder.Default
	private String country;
//	@NonNull
//	@Builder.Default
	private String type;

	public PostalAddressDTO(@NonNull String street, @NonNull String streetNumber, @NonNull String city, @NonNull String postalCode, @NonNull String country)
	{
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	@Override public @NonNull String getStreet() { return street; }
	@Override public void setStreet(@NonNull String street) { this.street = street; }

	@Override public @NonNull String getStreetNumber() { return streetNumber; }
	@Override public void setStreetNumber(@NonNull String streetNumber) { this.streetNumber = streetNumber; }

	@Override public @NonNull String getCity() { return city; }
	@Override public void setCity(@NonNull String city) { this.city = city; }

	@Override public @NonNull String getStateOrProvince() { return stateOrProvince; }
	@Override public void setStateOrProvince(String stateOrProvince) { this.stateOrProvince = stateOrProvince; }

	@Override public @NonNull String getPostalCode() { return postalCode; }
	@Override public void setPostalCode(@NonNull String postalCode) { this.postalCode = postalCode; }

	@Override public @NonNull String getCountry() { return country; }
	@Override public void setCountry(@NonNull String country) { this.country = country; }

	@Override public @NonNull String getType() { return type; }
	@Override public void setType(String type) { this.type = type; }

	@Override public void beforeMapping(@NonNull PostalAddressEntity input)
	{
		super.beforeMapping(input);
	}

	@Override public void afterMapping(@NonNull PostalAddressEntity input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull PostalAddressEntity toSource() { return Mapper.INSTANCE.map(this); }
}