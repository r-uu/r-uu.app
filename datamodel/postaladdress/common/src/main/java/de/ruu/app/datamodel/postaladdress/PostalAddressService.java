package de.ruu.app.datamodel.postaladdress;

import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface PostalAddressService<A extends PostalAddress>
{
	@NonNull A           create(@NonNull A    address);
	@NonNull Optional<A> read  (@NonNull Long id);
	@NonNull A           update(@NonNull A    address);
	         void        delete(@NonNull Long id);

	@NonNull Set<A>      findAll();
}