package de.ruu.app.datamodel.postaladdress;

import java.util.Optional;
import java.util.Set;

public interface PostalAddressService<A extends PostalAddress>
{
	A           create(A address);
	Optional<A> read(Long id);
	A           update(A company);
	void        delete(Long id);

	Set<A>      findAll();
}