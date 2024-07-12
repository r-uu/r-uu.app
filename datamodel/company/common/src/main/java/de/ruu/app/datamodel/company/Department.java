package de.ruu.app.datamodel.company;

import lombok.NonNull;

public interface Department
{
	@NonNull String     name();
	@NonNull Department name(@NonNull String name);

	/** @return non null {@link Department} */
	@NonNull Company company();
}