package de.ruu.app.jeeeraaah.common;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface Task
{
	@NonNull
	Long                          id();
	@NonNull
	String                        name();
	Optional<String>              description();
	Optional<LocalDate>           startEstimated();
	Optional<LocalDate>           finishEstimated();
	Optional<LocalDate>           startActual();
	Optional<LocalDate>           finishActual();
	Optional<Duration>            effortActual();

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
	Optional<? extends Task>      parent();

	/**
	 * @return subordinate tasks
	 *         <p>
	 *         optional unmodifiable set of {@link Task}s, optional supports lazy loading, empty optional means no attempt
	 *         was made to load children, present optional but empty {@code Set} means, no children could be loaded
	 */
	@NonNull
	Optional<Set<? extends Task>> children();
}