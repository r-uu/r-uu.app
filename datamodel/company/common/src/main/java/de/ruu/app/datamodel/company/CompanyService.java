package de.ruu.app.datamodel.company;

import java.util.Optional;
import java.util.Set;

public interface CompanyService<C extends Company>
{
	C           create(C company);
	Optional<C> read(Long id);
	C           update(C company);
	void        delete(Long id);

	Set<C>      findAll();
	Optional<C> findWithDepartments(Long id);
}