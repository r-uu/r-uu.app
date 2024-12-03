package de.ruu.app.jeeeraaah.common;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

/**
 * Generic, technology (JPA, JSONB, JAXB, MapStruct, ...) agnostic interface for task groups
 * @param <T> {@link Task} implementation for {@link #tasks()} belonging to this task group instance
 */
public interface TaskGroup<T extends Task<? extends TaskGroup<T>, T>>
{
	@NonNull String           name();
	@NonNull TaskGroup<T>     name(@NonNull String name);
	@NonNull Optional<String> description();
	@NonNull TaskGroup<T>     description(@Nullable String description);
	@NonNull Optional<Set<T>> tasks();
	boolean addTask   (T task);
	boolean removeTask(T task);
}