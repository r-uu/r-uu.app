package de.ruu.app.jeeeraaah.common;

import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

public interface TaskGroup
{
//	@NonNull UUID uuid();
	@NonNull String name();
	@NonNull Optional<Set<? extends Task>> tasks();
}