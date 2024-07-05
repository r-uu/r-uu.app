package de.ruu.app.datamodel.postaladdress.jpadto.dto;

import de.ruu.app.datamodel.postaladdress.PostalAddress;
import de.ruu.app.datamodel.postaladdress.jpadto.Mapper;
import de.ruu.app.datamodel.postaladdress.jpadto.jpa.PostalAddressEntity;
import de.ruu.lib.jpa.core.AbstractDTO;
import de.ruu.lib.jpa.core.mapstruct.AbstractMappedDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

@Slf4j
@Getter
@Setter
@Accessors(fluent = true) // generate fluent style getters but also implement java bean style getters
// to comply to java bean conventions
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by jpa
@AllArgsConstructor
@Builder
public class PostalAddressDTO extends AbstractMappedDTO<PostalAddressEntity> implements PostalAddress
{
	@NonNull @Builder.Default private String street = "";
	@NonNull @Builder.Default private String streetNumber = "";
	@NonNull @Builder.Default private String city = "";
	@NonNull @Builder.Default private String stateOrProvince = "";
	@NonNull @Builder.Default private String postalCode = "";
	@NonNull @Builder.Default private String country = "";
	@NonNull @Builder.Default private String type = "";

	@Override public @NonNull String getStreet() { return street; }
	@Override public void setStreet(@NonNull String street) { this.street = street; }

	@Override public @NonNull String getStreetNumber() { return streetNumber; }
	@Override public void setStreetNumber(@NonNull String streetNumber) { this.streetNumber = streetNumber; }

	@Override public @NonNull String getCity() { return city; }
	@Override public void setCity(@NonNull String city) { this.city = city; }

	@Override public @NonNull String getStateOrProvince() { return stateOrProvince; }
	@Override public void setStateOrProvince(@NonNull String stateOrProvince) { this.stateOrProvince = stateOrProvince; }

	@Override public @NonNull String getPostalCode() { return postalCode; }
	@Override public void setPostalCode(@NonNull String postalCode) { this.postalCode = postalCode; }

	@Override public @NonNull String getCountry() { return country; }
	@Override public void setCountry(@NonNull String country) { this.country = country; }

	@Override public @NonNull String getType() { return type; }
	@Override public void setType(@NonNull String type) { this.type = type; }

	@Override public void afterMapping(@NonNull PostalAddressEntity input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull PostalAddressEntity toSource() { return Mapper.INSTANCE.map(this); }
}