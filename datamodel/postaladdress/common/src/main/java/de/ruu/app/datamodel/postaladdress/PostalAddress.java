package de.ruu.app.datamodel.postaladdress;

import lombok.NonNull;

public interface PostalAddress
{
	@NonNull String getStreet         ();
	@NonNull String getStreetNumber   ();
	@NonNull String getCity           ();
	@NonNull String getStateOrProvince();
	@NonNull String getPostalCode     ();
	@NonNull String getCountry        ();
	@NonNull String getType           ();

	void setStreet         (@NonNull String street         );
	void setStreetNumber   (@NonNull String streetNumber   );
	void setCity           (@NonNull String city           );
	void setStateOrProvince(@NonNull String stateOrProvince);
	void setPostalCode     (@NonNull String postalCode     );
	void setCountry        (@NonNull String country        );
	void setType           (@NonNull String type           );

	@NonNull String street         ();
	@NonNull String streetNumber   ();
	@NonNull String city           ();
	@NonNull String stateOrProvince();
	@NonNull String postalCode     ();
	@NonNull String country        ();
	@NonNull String type           ();

//	@NonNull PostalAddressEntity street(         @NonNull String street         );
//	@NonNull PostalAddressEntity streetNumber(   @NonNull String streetNumber   );
//	@NonNull PostalAddressEntity city(           @NonNull String city           );
//	@NonNull PostalAddressEntity stateOrProvince(@NonNull String stateOrProvince);
//	@NonNull PostalAddressEntity postalCode(     @NonNull String postalCode     );
//	@NonNull PostalAddressEntity country(        @NonNull String country        );
//	@NonNull PostalAddressEntity type(           @NonNull String type           );
}