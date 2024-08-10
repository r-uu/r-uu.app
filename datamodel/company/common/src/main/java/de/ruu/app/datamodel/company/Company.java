package de.ruu.app.datamodel.company;

import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface Company
{
	/** @return nullable id */
	Long id();

	@NonNull String     name();
	@NonNull Company    name(@NonNull String name);
	@NonNull String  getName();
	@NonNull Company setName(@NonNull String name);

	/**
	 * @return optional unmodifiable set of {@link Department}s, optional supports lazy loading, empty optional means no
	 *         attempt was made to load optionalDepartments, present optional but empty set means, no optionalDepartments could be loaded
	 */
	Optional<Set<Department>> departments();
}