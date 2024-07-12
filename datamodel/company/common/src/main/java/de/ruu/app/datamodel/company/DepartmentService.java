package de.ruu.app.datamodel.company;

import java.util.Optional;

public interface DepartmentService<D extends Department>
{
	D           save  (D department);
	D           update(D department);
	Optional<D> find  (Long id);
	void        delete(Long id);
}