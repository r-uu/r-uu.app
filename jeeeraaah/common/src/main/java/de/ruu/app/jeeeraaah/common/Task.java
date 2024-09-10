package de.ruu.app.jeeeraaah.common;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface Task
{
//	@NonNull
//	UUID                          uuid();
	@NonNull
	TaskGroup                     taskGroup();
	@NonNull
	Long                          id();
	@NonNull
	String                        name();
	@Nullable
	String                        description();
	@NonNull
	default Optional<String>      descriptionOptional    () { return Optional.ofNullable(description()); };
	@Nullable
	LocalDate                     startEstimated();
	@NonNull
	default Optional<LocalDate>   startEstimatedOptional () { return Optional.ofNullable(startEstimated()); };
	@Nullable
	LocalDate                     finishEstimated();
	@NonNull
	default Optional<LocalDate>   finishEstimatedOptional() { return Optional.ofNullable(finishEstimated()); };
	@Nullable
	LocalDate                     startActual();
	@NonNull
	default Optional<LocalDate>   startActualOptional    () { return Optional.ofNullable(startActual()); };
	@Nullable
	LocalDate                     finishActual();
	@NonNull
	default Optional<LocalDate>   finishActualOptional   () { return Optional.ofNullable(finishActual()); };
	@Nullable
	Duration                      effortActual();
	@NonNull
	default Optional<Duration>    effortActualOptional   () { return Optional.ofNullable(effortActual()); };

	/**
	 * @return tasks that have to be finished before this task can start
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load predecessors, present optional but empty {@code Set} means, no predecessors could be
	 *         loaded
	 */
	@NonNull
	Optional<Set<? extends Task>> predecessors();

	/**
	 * @return tasks that can only start after this task is finished
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load successors, present optional but empty {@code Set} means, no successors could be loaded
	 */
	@NonNull
	Optional<Set<? extends Task>> successors();

	/** @return superordinate task */
	@NonNull
	Optional<? extends Task> optionalParent();

	/**
	 * @return subordinate tasks
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load children, present optional but empty {@code Set} means, no children could be loaded
	 */
	@NonNull
	Optional<Set<? extends Task>> children();
}