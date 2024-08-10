package de.ruu.app.datamodel.company;

import lombok.NonNull;

public interface Department
{
	/** @return nullable id */
	Long id();

	@NonNull String        name();
	@NonNull Department    name(@NonNull String name);
	@NonNull String     getName();
	@NonNull Department setName(@NonNull String name);

	/** @return non null {@link Company} */
	@NonNull Company    company();
	@NonNull Company getCompany();
}