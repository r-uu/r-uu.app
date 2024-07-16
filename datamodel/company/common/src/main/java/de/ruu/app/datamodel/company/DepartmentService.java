package de.ruu.app.datamodel.company;

import java.util.Optional;
import java.util.Set;

public interface DepartmentService<D extends Department>
{
	D           create(D department);
	Optional<D> read  (Long id);
	D           update(D department);
	void        delete(Long id);

	Set<D> findAll();
}