package de.ruu.app.datamodel.postaladdress.dto;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpadto.Mapper;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Setter
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
@NoArgsConstructor (access = AccessLevel.PROTECTED, force = true) // generate no  args constructor for mapstruct, ...
@RequiredArgsConstructor
@AllArgsConstructor       // generate all args constructor for builder
//@Builder                // DO NOT generate builder because that will break custom mapstruct mechanism for handling of
                          // entity id and version
public class PostalAddressDTO extends AbstractMappedDTO<PostalAddressEntity> implements PostalAddress
{
	@NonNull private String street;
	@NonNull private String streetNumber;
	@NonNull private String postalCode;
	@NonNull private String city;
	         private String stateOrProvince;
	@NonNull private String country;
	         private String type;

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

	@Override public @NonNull PostalAddressEntity toSource() { return Mapper.INSTANCE.map(this); }

	// make mapstruct callback methods available to public (mapper lives in another package)
	public void beforeMapping(@NonNull PostalAddressEntity input) { super.beforeMapping(input); }
	public void afterMapping( @NonNull PostalAddressEntity input) { super.afterMapping (input); }
}