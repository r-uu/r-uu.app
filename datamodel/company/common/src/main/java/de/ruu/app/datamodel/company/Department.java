package de.ruu.app.demo.common;

import lombok.NonNull;

public interface Department
{
	@NonNull String     name();
	@NonNull Department name(@NonNull String name);

	/** @return non null {@link Department} */
	@NonNull Company company();
}